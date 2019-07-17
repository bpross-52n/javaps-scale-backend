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
import java.util.ArrayList;
import java.util.List;

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
    "dependencies",
    "job_type",
    "name",
    "recipe_inputs"
})
public class Job implements Serializable {

    @JsonProperty("dependencies")
    private List<Object> dependencies = new ArrayList<>();
    @JsonProperty("job_type")
    private JobType jobType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("recipe_inputs")
    private List<RecipeInput> recipeInputs = new ArrayList<>();
    private final static long serialVersionUID = 5414953060204559319L;

    public Job() {
    }

    public Job(List<Object> dependencies, JobType jobType, String name, List<RecipeInput> recipeInputs) {
        super();
        this.dependencies = dependencies;
        this.jobType = jobType;
        this.name = name;
        this.recipeInputs = recipeInputs;
    }

    @JsonProperty("dependencies")
    public List<Object> getDependencies() {
        return dependencies;
    }

    @JsonProperty("dependencies")
    public void setDependencies(List<Object> dependencies) {
        this.dependencies = dependencies;
    }

    public Job withDependencies(List<Object> dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    @JsonProperty("job_type")
    public JobType getJobType() {
        return jobType;
    }

    @JsonProperty("job_type")
    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Job withJobType(JobType jobType) {
        this.jobType = jobType;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Job withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("recipe_inputs")
    public List<RecipeInput> getRecipeInputs() {
        return recipeInputs;
    }

    @JsonProperty("recipe_inputs")
    public void setRecipeInputs(List<RecipeInput> recipeInputs) {
        this.recipeInputs = recipeInputs;
    }

    public Job withRecipeInputs(List<RecipeInput> recipeInputs) {
        this.recipeInputs = recipeInputs;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("dependencies", dependencies).append("jobType", jobType).append("name", name).append("recipeInputs", recipeInputs).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(jobType).append(dependencies).append(recipeInputs).append(name).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Job == false) {
            return false;
        }
        Job rhs = (Job) other;
        return new EqualsBuilder().append(jobType, rhs.jobType).append(dependencies, rhs.dependencies).append(recipeInputs, rhs.recipeInputs).append(name, rhs.name).isEquals();
    }

}
