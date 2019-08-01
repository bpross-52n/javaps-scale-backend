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

import org.n52.javaps.backend.scale.api.QueueRecipe;
import org.n52.javaps.backend.scale.api.Recipe;
import org.n52.javaps.backend.scale.api.RecipeType;
import org.n52.javaps.backend.scale.api.RecipeTypes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
public interface ScaleService {

    String QUERY_PARAM_KEY_PAGE = "page";

    @GET("recipes/{id}/")
    Call<Recipe> getRecipe(@Header("Cookie") String dcosAuthCookie, @Path("id") int id);

    @GET("recipe-types/")
    Call<RecipeTypes> getRecipeTypes(@Header("Cookie") String dcosAuthCookie);

    @GET("recipe-types/")
    Call<RecipeTypes> getRecipeTypes(@Header("Cookie") String dcosAuthCookie, @Query("page") int page);

    @GET("recipe-types/{id}/")
    Call<RecipeType> getRecipeType(@Header("Cookie") String dcosAuthCookie, @Path("id") int id);

    @POST("queue/new-recipe/")
    Call<Void> schedule(@Header("Cookie") String dcosAuthCookie, @Body QueueRecipe queueRecipe);


}
