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
 * See  <a href="http://gmudcos.hopto.org/service/scale/docs/rest/job_type.html
 *#rest-job-type-list">REST API Job Type Services: Job Type List</a>.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "is_active"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobTypeReference implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 3031949327948349133L;

    @JsonProperty("id")
    private int id;

    @JsonProperty("is_active")
    private boolean isActive;

    public JobTypeReference() {
    }

    public JobTypeReference(int id, boolean isActive) {
        super();
        this.id = id;
        this.isActive = isActive;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public JobTypeReference withId(int id) {
        this.id = id;
        return this;
    }

    @JsonProperty("is_active")
    public boolean isIsActive() {
        return isActive;
    }

    @JsonProperty("is_active")
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public JobTypeReference withIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("isActive", isActive)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(isActive)
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof JobTypeReference)) {
            return false;
        }
        JobTypeReference rhs = (JobTypeReference) other;
        return new EqualsBuilder()
                .append(isActive, rhs.isActive)
                .append(id, rhs.id)
                .isEquals();
    }

}
