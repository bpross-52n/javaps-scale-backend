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

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.security.auth.Destroyable;

import org.n52.janmayen.lifecycle.Constructable;
import org.n52.javaps.backend.scale.api.Recipe;
import org.n52.javaps.backend.scale.api.Recipes;
import org.n52.javaps.backend.scale.api.ReferencedRecipe;
import org.n52.javaps.backend.scale.api.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
public class ScaleServiceController implements Constructable, Destroyable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScaleServiceController.class);

    @Inject
    private ScaleConfig configuration;

    private Retrofit retrofit;

    private ScaleService scaleService;

    private Converter converter;

    public ScaleServiceController() {
        LOGGER.info("NEW {}", this);
    }

    public List<ScaleAlgorithm> getAlgorithms() throws IOException {
        Call<Recipes> call = scaleService.getRecipes(getAuthCookieContent());
        Response<Recipes> response = call.execute();
        List<ScaleAlgorithm> result = new LinkedList<>();
        if (response.isSuccessful()) {
            Recipes recipes = response.body();
            do {
                for (ReferencedRecipe recipeReference : recipes.getResults()) {
                    if (recipeReference.isIsSuperseded()) {
                        continue;
                    }
                    Call<Recipe> recipeCall = scaleService.getRecipe(
                            getAuthCookieContent(),
                            recipeReference.getId());
                    Response<Recipe> recipeResponse = recipeCall.execute();
                    if (recipeResponse.isSuccessful()) {
                        Recipe recipe = recipeResponse.body();
                        result.add(converter.convertToAlgorithm(recipe));
                    }
                }
            } while (recipes.getNext() != null);
            if (result.isEmpty()) {
                return Collections.emptyList();
            } else {
                return Collections.unmodifiableList(result);
            }
        }
        LOGGER.error("Requesting job-types from scale web server failed!\n{}", response.errorBody());
        return Collections.emptyList();
    }

    private String getAuthCookieContent() {
        return String.format("dcos-acs-auth-cookie=%s; dcos-acs-info-cookie=%s",
                configuration.getJWTAuthToken().orElse("not-specified"),
                configuration.getInfoCookie().orElse("not-specified"));
    }

    @Override
    public void init() {
        LOGGER.trace("START INIT {}", this);
        Optional<URL> optional = configuration.getScaleWebserverEndpoint();
        if(!optional.isPresent()) {
            LOGGER.error("Could not get base url of scale webserver -> cancel init.");
            // FIXME mark as not working!
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(optional.get())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            scaleService = retrofit.create(ScaleService.class);
            converter = new Converter(this);
        }
        LOGGER.info("INIT {}", this);
    }

}
