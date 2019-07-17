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
    "is_superseded"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ReferencedRecipe implements Serializable {

    @JsonProperty("id")
    private int id;
    @JsonProperty("is_superseded")
    private boolean isSuperseded;

    private final static long serialVersionUID = 1000474927307839313L;

    public ReferencedRecipe() {
    }

    public ReferencedRecipe(int id, boolean isSuperseded) {
        super();
        this.id = id;
        this.isSuperseded = isSuperseded;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public ReferencedRecipe withId(int id) {
        this.id = id;
        return this;
    }

     @JsonProperty("is_superseded")
    public boolean isIsSuperseded() {
        return isSuperseded;
    }

    @JsonProperty("is_superseded")
    public void setIsSuperseded(boolean isSuperseded) {
        this.isSuperseded = isSuperseded;
    }

    public ReferencedRecipe withIsSuperseded(boolean isSuperseded) {
        this.isSuperseded = isSuperseded;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("isSuperseded", isSuperseded).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(isSuperseded).append(id).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof ReferencedRecipe == false) {
            return false;
        }
        ReferencedRecipe rhs = (ReferencedRecipe) other;
        return new EqualsBuilder().append(isSuperseded, rhs.isSuperseded).append(id, rhs.id).isEquals();
    }

}