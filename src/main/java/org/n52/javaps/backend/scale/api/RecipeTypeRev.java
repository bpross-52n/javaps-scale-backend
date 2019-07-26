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
    "revision_num",
    "definition",
    "created"
})
public class RecipeTypeRev implements Serializable {

    @JsonProperty("id")
    private long id;

    @JsonProperty("recipe_type")
    private ReferencedRecipeType recipeType;

    @JsonProperty("revision_num")
    private int revision;

    @JsonProperty("definition")
    private Definition definition;

    @JsonProperty("created")
    private String created;

    @JsonIgnore
    private final static long serialVersionUID = -5478867691231506657L;

    public RecipeTypeRev() {
    }

    public RecipeTypeRev(long id, ReferencedRecipeType recipeType, int revision, Definition definition, String created) {
        super();
        this.id = id;
        this.recipeType = recipeType;
        this.revision = revision;
        this.definition = definition;
        this.created = created;
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    public RecipeTypeRev withId(long id) {
        this.id = id;
        return this;
    }

    @JsonProperty("recipe_type")
    public ReferencedRecipeType getRecipeType() {
        return recipeType;
    }

    @JsonProperty("recipe_type")
    public void setRecipeType(ReferencedRecipeType recipeType) {
        this.recipeType = recipeType;
    }

    public RecipeTypeRev withRecipeType(ReferencedRecipeType recipeType) {
        this.recipeType = recipeType;
        return this;
    }

    @JsonProperty("revision_num")
    public int getRevision() {
        return revision;
    }

    @JsonProperty("revision_num")
    public void setRevision(int revision) {
        this.revision = revision;
    }

    public RecipeTypeRev withRevision(int revision) {
        this.revision = revision;
        return this;
    }

    @JsonProperty("definition")
    public Definition getDefinition() {
        return definition;
    }

    @JsonProperty("definition")
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    public RecipeTypeRev withDefinition(Definition definition) {
        this.definition = definition;
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

    public RecipeTypeRev withCreated(String created) {
        this.created = created;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("recipeType", recipeType).append("revision", revision).append("definition", definition).append("created", created).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(recipeType).append(id).append(definition).append(created).append(revision).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof RecipeTypeRev == false) {
            return false;
        }
        RecipeTypeRev rhs = (RecipeTypeRev) other;
        return new EqualsBuilder().append(recipeType, rhs.recipeType).append(id, rhs.id).append(definition, rhs.definition).append(created, rhs.created).append(revision, rhs.revision).isEquals();
    }

}
