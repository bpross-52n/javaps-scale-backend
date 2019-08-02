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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Abstract superclass for the implementation of <code>input_data</code>
 * properties of {@link Definition}.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 * @see Definition
 * @see InputDatumFile
 * @see InputDatumFiles
 * @see InputDatumProperty
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "required"
})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InputDatumProperty.class, name = "property"),
        @JsonSubTypes.Type(value = InputDatumFile.class, name = "file"),
        @JsonSubTypes.Type(value = InputDatumFiles.class, name = "files")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class InputDatum {

    @JsonProperty("required")
    private boolean required;

    @JsonProperty("name")
    private String name;

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
        return new ToStringBuilder(this).append("required", required).append("name", name).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(required).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof InputDatum)) {
            return false;
        }
        InputDatum rhs = (InputDatum) other;
        return new EqualsBuilder().append(name, rhs.name).append(required, rhs.required).isEquals();
    }

}
