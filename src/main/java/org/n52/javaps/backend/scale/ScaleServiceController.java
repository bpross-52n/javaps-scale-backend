/*
 * Copyright 2019-2019 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.javaps.backend.scale;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.security.auth.Destroyable;

import org.n52.janmayen.lifecycle.Constructable;
import org.n52.javaps.backend.scale.api.Job;
import org.n52.javaps.backend.scale.api.Jobs;
import org.n52.javaps.backend.scale.api.QueueRecipe;
import org.n52.javaps.backend.scale.api.Recipe;
import org.n52.javaps.backend.scale.api.RecipeType;
import org.n52.javaps.backend.scale.api.RecipeTypes;
import org.n52.javaps.backend.scale.api.ReferencedRecipeType;
import org.n52.javaps.backend.scale.api.util.Converter;
import org.n52.javaps.backend.scale.api.util.ScaleAuthorizationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * TODO add more log statements
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
public class ScaleServiceController implements Constructable, Destroyable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScaleServiceController.class);

    @Inject
    private ScaleConfiguration configuration;

    private Retrofit retrofit;

    private ScaleService scaleService;

    private Converter converter;

    public ScaleServiceController() {
        LOGGER.info("NEW {}", this);
    }

    public List<ScaleAlgorithm> getAlgorithms() throws IOException, ScaleAuthorizationFailedException {
        List<ScaleAlgorithm> result = new LinkedList<>();
        RecipeTypes recipeTypes = null;
        do {
            Call<RecipeTypes> call = scaleService.getRecipeTypes(getAuthCookieContent());
            if (recipeTypes != null && recipeTypes.getNext() != null) {
                int page = Integer.parseInt(
                        UriComponentsBuilder.fromUriString(recipeTypes.getNext().toExternalForm()).build()
                        .getQueryParams().get(ScaleService.QUERY_PARAM_KEY_PAGE).get(0));
                call = scaleService.getRecipeTypes(getAuthCookieContent(), page);
            }
            Response<RecipeTypes> response = call.execute();
            if (!response.isSuccessful()) {
                LOGGER.error("Requesting recipe types from scale web server failed!\n{}", response.errorBody());
                if (isUnauthorized(response)) {
                    throw new ScaleAuthorizationFailedException();
                }
                // TODO improve error handling using response codes and headers
                throw new IOException(response.errorBody().toString());
            }
            recipeTypes = response.body();
            for (ReferencedRecipeType recipeReference : recipeTypes.getResults()) {
                if (!recipeReference.isIsActive()) {
                    continue;
                }
                Call<RecipeType> recipeTypeCall = scaleService.getRecipeType(
                        getAuthCookieContent(),
                        recipeReference.getId());
                Response<RecipeType> recipeTypeResponse = recipeTypeCall.execute();
                if (!recipeTypeResponse.isSuccessful()) {
                    LOGGER.error("Requesting recipe '{}' from scale web server failed!\n{}",
                            recipeTypeResponse.raw().request().url(),
                            recipeTypeResponse.errorBody());
                    if (isUnauthorized(recipeTypeResponse)) {
                        throw new ScaleAuthorizationFailedException();
                    }
                    continue;
                }
                RecipeType recipeType = recipeTypeResponse.body();
                result.add(converter.convertToAlgorithm(recipeType));
            }
        } while (recipeTypes != null && recipeTypes.getNext() != null);
        if (result.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(result);
        }
    }

    public int queue(QueueRecipe queueRecipe) throws IOException, ScaleAuthorizationFailedException {
        Call<Void> call = scaleService.schedule(getAuthCookieContent(), queueRecipe);
        Response<Void> response = call.execute();
        if (!response.isSuccessful()) {
            LOGGER.error("Could not queue recipe!\n{}", response.errorBody());
            if (isUnauthorized(response)) {
                throw new ScaleAuthorizationFailedException();
            }
            return -1;
        }
        List<String> locations = response.headers().values("Location");
        if (locations.isEmpty()) {
            return -1;
        }
        if (locations.size() > 1) {
            throw new IllegalArgumentException("More than one 'Location' header found in response from scale API");
        }
        return Integer.parseInt(new File(new URL(locations.get(0)).getPath()).getName());
    }

    private boolean isUnauthorized(Response<?> response) {
        return response.code() == HttpURLConnection.HTTP_UNAUTHORIZED;
    }

    private String getAuthCookieContent() {
        return String.format("dcos-acs-auth-cookie=%s; dcos-acs-info-cookie=%s",
                configuration.getJWTAuthToken().orElse(ScaleConfiguration.CONFIGURATION_VALUE_NOT_SPECIFIED),
                configuration.getInfoCookie().orElse(ScaleConfiguration.CONFIGURATION_VALUE_NOT_SPECIFIED));
    }

    @Override
    public void init() {
        LOGGER.trace("START INIT {}", this);
        Optional<URL> baseUrl = configuration.getWebserverEndpoint();
        if (!baseUrl.isPresent()) {
            LOGGER.error("Could not get base url of scale webserver -> cancel init.");
            // FIXME mark as not working!
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl.get())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            scaleService = retrofit.create(ScaleService.class);
            converter = new Converter(this);
        }
        LOGGER.info("INIT {}", this);
    }

    public Recipe waitForRecipe(int queuedRecipeId) throws IOException, ScaleAuthorizationFailedException {
        // TODO add max wait time!?
        Recipe recipe = null;
        boolean continueWaiting;
        do {
            continueWaiting = false;
            Call<Recipe> call = scaleService.getRecipe(getAuthCookieContent(), queuedRecipeId);
            Response<Recipe> response = call.execute();
            if (!response.isSuccessful()) {
                LOGGER.error("Requesting recipes from scale web server failed!\n{}", response.errorBody());
                if (isUnauthorized(response)) {
                    throw new ScaleAuthorizationFailedException();
                }

            }
            recipe = response.body();
            // if completed is null
            if (recipe.getCompleted() == null) {
                // for each job
                for (Jobs job : recipe.getJobs()) {
                    // if status not failed or not completed or not blocked
                    if (requiresFurtherWaiting(job.getJob().getStatus())) {
                        // continue (sleep n seconds)
                        // n needs to be configured
                        try {
                            Thread.sleep(configuration.getWaitingSleepInSeconds() * 1000);
                        } catch (InterruptedException e) {
                            LOGGER.trace("Sleep interrupted", e);
                        }
                        continueWaiting = true;
                        break;
                    }
                }
            } else {
                break;
            }
        } while (continueWaiting);
        return recipe;
    }

    private boolean requiresFurtherWaiting(String status) {
        return !Job.Status.BLOCKED.name().equals(status) &&
                !Job.Status.FAILED.name().equals(status) &&
                !Job.Status.COMPLETED.name().equals(status);
    }

}
