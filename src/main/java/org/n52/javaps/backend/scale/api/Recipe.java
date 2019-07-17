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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    "id",
    "recipe_type",
    "recipe_type_rev",
    "created",
    "last_modified"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe implements Serializable {

    @JsonProperty("id")
    private int id;
    @JsonProperty("recipe_type")
    private RecipeType recipeType;
    @JsonProperty("recipe_type_rev")
    private RecipeTypeRev recipeTypeRev;
    @JsonProperty("created")
    private String created;
    @JsonProperty("last_modified")
    private String lastModified;
    @JsonIgnore

    private final static long serialVersionUID = 3110533031488141475L;

    public Recipe() {
    }

    public Recipe(int id, RecipeType recipeType, RecipeTypeRev recipeTypeRev, String created, String lastModified) {
        super();
        this.id = id;
        this.recipeType = recipeType;
        this.recipeTypeRev = recipeTypeRev;
        this.created = created;
        this.lastModified = lastModified;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public Recipe withId(int id) {
        this.id = id;
        return this;
    }

    @JsonProperty("recipe_type")
    public RecipeType getRecipeType() {
        return recipeType;
    }

    @JsonProperty("recipe_type")
    public void setRecipeType(RecipeType recipeType) {
        this.recipeType = recipeType;
    }

    public Recipe withRecipeType(RecipeType recipeType) {
        this.recipeType = recipeType;
        return this;
    }

    @JsonProperty("recipe_type_rev")
    public RecipeTypeRev getRecipeTypeRev() {
        return recipeTypeRev;
    }

    @JsonProperty("recipe_type_rev")
    public void setRecipeTypeRev(RecipeTypeRev recipeTypeRev) {
        this.recipeTypeRev = recipeTypeRev;
    }

    public Recipe withRecipeTypeRev(RecipeTypeRev recipeTypeRev) {
        this.recipeTypeRev = recipeTypeRev;
        return this;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    public Recipe withCreated(String created) {
        this.created = created;
        return this;
    }

    @JsonProperty("last_modified")
    public String getLastModified() {
        return lastModified;
    }

    @JsonProperty("last_modified")
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Recipe withLastModified(String lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("recipeType", recipeType).append("recipeTypeRev", recipeTypeRev).append("created", created).append("lastModified", lastModified).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(recipeType).append(id).append(lastModified).append(created).append(recipeTypeRev).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Recipe == false) {
            return false;
        }
        Recipe rhs = (Recipe) other;
        return new EqualsBuilder().append(recipeType, rhs.recipeType).append(id, rhs.id).append(lastModified, rhs.lastModified).append(created, rhs.created).append(recipeTypeRev, rhs.recipeTypeRev).isEquals();
    }

}
