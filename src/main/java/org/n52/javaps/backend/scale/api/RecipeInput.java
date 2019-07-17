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
package org.n52.javaps.backend.scale.api;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "job_input",
    "recipe_input"
})
public class RecipeInput implements Serializable {

    @JsonProperty("job_input")
    private String jobInput;
    @JsonProperty("recipe_input")
    private String recipeInput;
    private final static long serialVersionUID = -4640730651844868374L;

    public RecipeInput() {
    }

    public RecipeInput(String jobInput, String recipeInput) {
        super();
        this.jobInput = jobInput;
        this.recipeInput = recipeInput;
    }

    @JsonProperty("job_input")
    public String getJobInput() {
        return jobInput;
    }

    @JsonProperty("job_input")
    public void setJobInput(String jobInput) {
        this.jobInput = jobInput;
    }

    public RecipeInput withJobInput(String jobInput) {
        this.jobInput = jobInput;
        return this;
    }

    @JsonProperty("recipe_input")
    public String getRecipeInput() {
        return recipeInput;
    }

    @JsonProperty("recipe_input")
    public void setRecipeInput(String recipeInput) {
        this.recipeInput = recipeInput;
    }

    public RecipeInput withRecipeInput(String recipeInput) {
        this.recipeInput = recipeInput;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("jobInput", jobInput).append("recipeInput", recipeInput).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(recipeInput).append(jobInput).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof RecipeInput == false) {
            return false;
        }
        RecipeInput rhs = (RecipeInput) other;
        return new EqualsBuilder().append(recipeInput, rhs.recipeInput).append(jobInput, rhs.jobInput).isEquals();
    }

}
