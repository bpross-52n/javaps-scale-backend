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
 * http://gmudcos.hopto.org/service/scale/docs/architecture/jobs/job_interface.html#architecture-jobs-interface-spec
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "input_data",
    "output_data"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Interface implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 577712678739685957L;

    @JsonProperty("input_data")
    private List<InputDatum> inputData;

    @JsonProperty("output_data")
    private List<OutputDatum> outputData;

    public Interface() {
    }

    public Interface(List<InputDatum> inputData, List<OutputDatum> outputData) {
        super();
        this.inputData = inputData;
        this.outputData = outputData;
    }

    @JsonProperty("input_data")
    public List<InputDatum> getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(List<InputDatum> inputData) {
        this.inputData = inputData;
    }

    public Interface withInputData(List<InputDatum> inputData) {
        this.inputData = inputData;
        return this;
    }

    @JsonProperty("output_data")
    public List<OutputDatum> getOutputData() {
        return outputData;
    }

    @JsonProperty("output_data")
    public void setOutputData(List<OutputDatum> outputData) {
        this.outputData = outputData;
    }

    public Interface withOutputData(List<OutputDatum> outputData) {
        this.outputData = outputData;
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("inputData", inputData)
                .append("outputData", outputData)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(inputData)
                .append(outputData)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Interface)) {
            return false;
        }
        Interface rhs = (Interface) other;
        return new EqualsBuilder()
                .append(inputData, rhs.inputData)
                .append(outputData, rhs.outputData)
                .isEquals();
    }

}
