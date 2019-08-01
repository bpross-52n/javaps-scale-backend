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
    "name",
    "required",
    "media_types"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputDatumFile extends InputDatum implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 4338781105929133794L;

    @JsonProperty("media_types")
    private List<String> mediaTypes = new ArrayList<>();

    public InputDatumFile() {
    }

    public InputDatumFile(String name, boolean required, List<String> mediaTypes) {
        setName(name);
        setRequired(required);
        this.mediaTypes = mediaTypes;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", getName())
                .append("required", isRequired())
                .append("mediaTypes", mediaTypes)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getName())
                .append(isRequired())
                .append(mediaTypes)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof InputDatumFile)) {
            return false;
        }
        InputDatumFile rhs = (InputDatumFile) other;
        return new EqualsBuilder()
                .append(getName(), rhs.getName())
                .append(isRequired(), rhs.isRequired())
                .append(mediaTypes, rhs.mediaTypes)
                .isEquals();
    }

}
