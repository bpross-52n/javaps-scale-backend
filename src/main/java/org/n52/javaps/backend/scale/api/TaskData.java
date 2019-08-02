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

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.n52.javaps.backend.scale.api.RecipeData.InputData;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Abstract superclass for {@link JobData} and {@link RecipeData}
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 * @see JobData
 * @see RecipeData
 */
public abstract class TaskData {

    @JsonProperty("version")
    protected String version = "1.0";

    @JsonProperty("input_data")
    protected List<InputData> inputData;

    public TaskData() {
        super();
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public TaskData withVersion(String version) {
        this.version = version;
        return this;
    }

    @JsonProperty("input_data")
    public List<InputData> getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(List<InputData> inputData) {
        this.inputData = inputData;
    }

    public TaskData withInputData(List<InputData> inputData) {
        this.inputData = inputData;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("version", version)
                .append("inputData", inputData)
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
        if (!(other instanceof TaskData)) {
            return false;
        }
        TaskData rhs = (TaskData) other;
        return new EqualsBuilder()
                .append(inputData, rhs.inputData)
                .append(version, rhs.version)
                .isEquals();
    }

}