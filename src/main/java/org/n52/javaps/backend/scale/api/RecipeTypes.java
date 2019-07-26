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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    "count",
    "next",
    "previous",
    "results"
})
public class RecipeTypes implements Serializable {

    @JsonProperty("count")
    private long count;

    @JsonProperty("next")
    private URL next;

    @JsonProperty("previous")
    private URL previous;

    @JsonProperty("results")
    private List<ReferencedRecipeType> results = new ArrayList<>();

    @JsonIgnore
    private final static long serialVersionUID = -3862307913807334087L;

    public RecipeTypes() {
    }

    public RecipeTypes(long count, URL next, URL previous, List<ReferencedRecipeType> results) {
        super();
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    @JsonProperty("count")
    public long getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(long count) {
        this.count = count;
    }

    public RecipeTypes withCount(long count) {
        this.count = count;
        return this;
    }

    @JsonProperty("next")
    public URL getNext() {
        return next;
    }

    @JsonProperty("next")
    public void setNext(URL next) {
        this.next = next;
    }

    public RecipeTypes withNext(URL next) {
        this.next = next;
        return this;
    }

    @JsonProperty("previous")
    public URL getPrevious() {
        return previous;
    }

    @JsonProperty("previous")
    public void setPrevious(URL previous) {
        this.previous = previous;
    }

    public RecipeTypes withPrevious(URL previous) {
        this.previous = previous;
        return this;
    }

    @JsonProperty("results")
    public List<ReferencedRecipeType> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<ReferencedRecipeType> results) {
        this.results = results;
    }

    public RecipeTypes withResults(List<ReferencedRecipeType> results) {
        this.results = results;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("count", count).append("next", next).append("previous", previous).append("results", results).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(results).append(previous).append(count).append(next).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof RecipeTypes == false) {
            return false;
        }
        RecipeTypes rhs = (RecipeTypes) other;
        return new EqualsBuilder().append(results, rhs.results).append(previous, rhs.previous).append(count, rhs.count).append(next, rhs.next).isEquals();
    }

}