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
    "input_data",
    "version"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Definition implements Serializable {

    @JsonProperty("input_data")
    private List<InputDatum> inputData = new ArrayList<>();

    @JsonProperty("version")
    private String version;

    @JsonIgnore
    private final static long serialVersionUID = 6291516404977457466L;

    public Definition() {
    }

    public Definition(List<InputDatum> inputData, String version) {
        super();
        this.inputData = inputData;
        this.version = version;
    }

    @JsonProperty("input_data")
    public List<InputDatum> getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(List<InputDatum> inputData) {
        this.inputData = inputData;
    }

    public Definition withInputData(List<InputDatum> inputData) {
        this.inputData = inputData;
        return this;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public Definition withVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("inputData", inputData)
                .append("version", version)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(inputData)
                .append(version)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Definition == false) {
            return false;
        }
        Definition rhs = (Definition) other;
        return new EqualsBuilder()
                .append(inputData, rhs.inputData)
                .append(version, rhs.version)
                .isEquals();
    }

}
