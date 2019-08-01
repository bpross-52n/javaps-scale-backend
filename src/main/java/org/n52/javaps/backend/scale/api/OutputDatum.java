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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "required",
    "media_type"
})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OutputDatum.class, name = "file"),
        @JsonSubTypes.Type(value = OutputDatum.class, name = "files")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputDatum implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 8597744226131781034L;

    @JsonProperty("required")
    private boolean required;

    @JsonProperty("name")
    private String name;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("required")
    public boolean isRequired() {
        return required;
    }

    @JsonProperty("required")
    public void setRequired(boolean required) {
        this.required = required;
    }

    public OutputDatum withRequired(boolean required) {
        this.required = required;
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

    public OutputDatum withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("media_type")
    public String getMediaType() {
        return mediaType;
    }

    @JsonProperty("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public OutputDatum withMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("required", required)
                .append("name", name)
                .append("mediaType", mediaType)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(required)
                .append(mediaType)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof OutputDatum)) {
            return false;
        }
        OutputDatum rhs = (OutputDatum) other;
        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(required, rhs.required)
                .append(mediaType, rhs.mediaType)
                .isEquals();
    }

}
