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
    "media_types",
    "required",
    "type",
    "name"
})
public class InputDatum implements Serializable {

    @JsonProperty("media_types")
    private List<String> mediaTypes = new ArrayList<>();
    @JsonProperty("required")
    private boolean required;
    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;
    private final static long serialVersionUID = -8385028383865937124L;

    public InputDatum() {
    }

    public InputDatum(List<String> mediaTypes, boolean required, String type, String name) {
        super();
        this.mediaTypes = mediaTypes;
        this.required = required;
        this.type = type;
        this.name = name;
    }

    @JsonProperty("media_types")
    public List<String> getMediaTypes() {
        return mediaTypes;
    }

    @JsonProperty("media_types")
    public void setMediaTypes(List<String> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public InputDatum withMediaTypes(List<String> mediaTypes) {
        this.mediaTypes = mediaTypes;
        return this;
    }

    @JsonProperty("required")
    public boolean isRequired() {
        return required;
    }

    @JsonProperty("required")
    public void setRequired(boolean required) {
        this.required = required;
    }

    public InputDatum withRequired(boolean required) {
        this.required = required;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public InputDatum withType(String type) {
        this.type = type;
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

    public InputDatum withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("mediaTypes", mediaTypes).append("required", required).append("type", type).append("name", name).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(mediaTypes).append(type).append(required).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof InputDatum == false) {
            return false;
        }
        InputDatum rhs = (InputDatum) other;
        return new EqualsBuilder().append(name, rhs.name).append(mediaTypes, rhs.mediaTypes).append(type, rhs.type).append(required, rhs.required).isEquals();
    }

}
