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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.security.auth.Destroyable;

import org.n52.janmayen.lifecycle.Constructable;
import org.n52.javaps.backend.scale.api.Job;
import org.n52.javaps.backend.scale.api.JobType;
import org.n52.javaps.backend.scale.api.JobTypes;
import org.n52.javaps.backend.scale.api.Jobs;
import org.n52.javaps.backend.scale.api.QueueJob;
import org.n52.javaps.backend.scale.api.QueueRecipe;
import org.n52.javaps.backend.scale.api.Recipe;
import org.n52.javaps.backend.scale.api.RecipeType;
import org.n52.javaps.backend.scale.api.RecipeTypes;
import org.n52.javaps.backend.scale.api.JobTypeReference;
import org.n52.javaps.backend.scale.api.RecipeTypeReference;
import org.n52.javaps.backend.scale.api.Task;
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

    public List<ScaleAlgorithm> getAlgorithms() throws IOException, ScaleAuthorizationFailedException {
        List<ScaleAlgorithm> result = new LinkedList<>();
        processRecipeTypes(result);
        processJobTypes(result);
        if (result.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(result);
        }
    }

    // TODO reduce code duplication
    private void processJobTypes(List<ScaleAlgorithm> result)
            throws NumberFormatException, IOException, ScaleAuthorizationFailedException {
        JobTypes jobTypes = null;
        do {
            Call<JobTypes> jobTypesCall = scaleService.getJobTypes(getAuthCookieContent());
            if (jobTypes != null && jobTypes.getNext() != null) {
                int page = Integer.parseInt(
                        UriComponentsBuilder.fromUriString(jobTypes.getNext().toExternalForm()).build()
                        .getQueryParams().get(ScaleService.QUERY_PARAM_KEY_PAGE).get(0));
                jobTypesCall = scaleService.getJobTypes(getAuthCookieContent(), page);
            }
            Response<JobTypes> jobTypesResponse = jobTypesCall.execute();
            if (!jobTypesResponse.isSuccessful()) {
                LOGGER.error("Requesting job types from scale web server failed!\n{}", jobTypesResponse.errorBody());
                if (isUnauthorized(jobTypesResponse)) {
                    throw new ScaleAuthorizationFailedException();
                }
                // TODO improve error handling using response codes and headers
                throw new IOException(jobTypesResponse.errorBody().toString());
            }
            jobTypes = jobTypesResponse.body();
            for (JobTypeReference jobReference : jobTypes.getResults()) {
                if (!jobReference.isIsActive()) {
                    continue;
                }
                Call<JobType> jobTypeCall = scaleService.getJobType(
                        getAuthCookieContent(),
                        jobReference.getId());
                Response<JobType> jobTypeResponse = jobTypeCall.execute();
                if (!jobTypeResponse.isSuccessful()) {
                    LOGGER.error("Requesting job type '{}' from scale web server failed!\n{}",
                            jobTypeResponse.raw().request().url(),
                            jobTypeResponse.errorBody());
                    if (isUnauthorized(jobTypeResponse)) {
                        throw new ScaleAuthorizationFailedException();
                    }
                    continue;
                }
                JobType jobType = jobTypeResponse.body();
                result.add(converter.convertToAlgorithm(jobType));
            }
        } while (jobTypes.getNext() != null);
    }

    // TODO reduce code duplication
    private void processRecipeTypes(List<ScaleAlgorithm> result)
            throws NumberFormatException, IOException, ScaleAuthorizationFailedException {
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
            for (RecipeTypeReference recipeReference : recipeTypes.getResults()) {
                if (!recipeReference.isIsActive()) {
                    continue;
                }
                Call<RecipeType> recipeTypeCall = scaleService.getRecipeType(
                        getAuthCookieContent(),
                        recipeReference.getId());
                Response<RecipeType> recipeTypeResponse = recipeTypeCall.execute();
                if (!recipeTypeResponse.isSuccessful()) {
                    LOGGER.error("Requesting recipe type '{}' from scale web server failed!\n{}",
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
        } while (recipeTypes.getNext() != null);
    }

    private String getAuthCookieContent() {
        return String.format("dcos-acs-auth-cookie=%s; dcos-acs-info-cookie=%s",
                configuration.getJWTAuthToken().orElse(ScaleConfiguration.CONFIGURATION_VALUE_NOT_SPECIFIED),
                configuration.getInfoCookie().orElse(ScaleConfiguration.CONFIGURATION_VALUE_NOT_SPECIFIED));
    }

    private boolean isUnauthorized(Response<?> response) {
        return response.code() == HttpURLConnection.HTTP_UNAUTHORIZED;
    }

    public int queue(QueueRecipe queueRecipe) throws IOException, ScaleAuthorizationFailedException {
        Call<Void> call = scaleService.schedule(getAuthCookieContent(), queueRecipe);
        return executeScheduleCall(call);
    }

    public int queue(QueueJob queueJob) throws IOException, ScaleAuthorizationFailedException {
        Call<Void> call = scaleService.schedule(getAuthCookieContent(), queueJob);
        return executeScheduleCall(call);
    }

    private int executeScheduleCall(Call<Void> call) throws IOException, ScaleAuthorizationFailedException,
            IllegalArgumentException, NumberFormatException, MalformedURLException {
        Response<Void> response = call.execute();
        if (!response.isSuccessful()) {
            LOGGER.error("Could not queue task!\n{}", response.errorBody());
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

    public Recipe waitForRecipe(int queuedRecipeId) throws IOException, ScaleAuthorizationFailedException {
        // TODO add max wait time!?
        Recipe recipe = null;
        boolean continueWaiting;
        do {
            continueWaiting = false;
            Call<Recipe> call = scaleService.getRecipe(getAuthCookieContent(), queuedRecipeId);
            Response<Recipe> response = call.execute();
            if (!response.isSuccessful()) {
                LOGGER.error("Requesting recipe from scale web server failed!\n{}", response.errorBody());
                if (isUnauthorized(response)) {
                    throw new ScaleAuthorizationFailedException();
                }

            }
            recipe = response.body();
            // if completed is null
            if (recipe.getCompleted() == null) {
                continueWaiting = checkForSleep(recipe);
            } else {
                break;
            }
        } while (continueWaiting);
        return recipe;
    }

    public Job waitForJob(int queuedJobId) throws IOException, ScaleAuthorizationFailedException {
        // TODO add max wait time!?
        Job job = null;
        boolean continueWaiting;
        do {
            continueWaiting = false;
            Call<Job> call = scaleService.getJob(getAuthCookieContent(), queuedJobId);
            Response<Job> response = call.execute();
            if (!response.isSuccessful()) {
                LOGGER.error("Requesting job from scale web server failed!\n{}", response.errorBody());
                if (isUnauthorized(response)) {
                    throw new ScaleAuthorizationFailedException();
                }

            }
            job = response.body();
            // if completed is null
            if (job.getCompleted() == null) {
                continueWaiting = checkJobForSleep(job);
            } else {
                break;
            }
        } while (continueWaiting);
        return job;
    }

    private boolean checkForSleep(Task task) {
        if (task instanceof Recipe) {
            // for each job
            for (Jobs job : ((Recipe) task).getJobs()) {
                checkJobForSleep(job.getJob());
            }
        } else if (task instanceof Job) {
            checkJobForSleep((Job) task);
        }
        return false;
    }

    private boolean checkJobForSleep(Job job) {
        // if status not failed or not completed or not blocked
        String status = job.getStatus();
        if (!Job.Status.BLOCKED.name().equals(status) &&
                !Job.Status.FAILED.name().equals(status) &&
                !Job.Status.COMPLETED.name().equals(status)) {
            // continue (sleep n seconds)
            // n needs to be configured
            try {
                Thread.sleep(configuration.getWaitingSleepInSeconds() * 1000);
            } catch (InterruptedException e) {
                LOGGER.trace("Sleep interrupted", e);
            }
            return true;
        }
        return false;
    }

}
