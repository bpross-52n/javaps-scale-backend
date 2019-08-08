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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Implementation of <a href="http://gmudcos.hopto.org/service/scale/docs/architecture/jobs/job_data.html
 *#job-data-specification-version-1-0">Job Data Specification Version 1.0</a>.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 * @see TaskData
 * @see RecipeData
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "version",
        "input_data",
        "output_data"
})
public class JobData extends TaskData implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = -347491433199918030L;

    @JsonProperty("output_data")
    private List<OutputData> outputData;

    @JsonProperty("output_data")
    public List<OutputData> getOutputData() {
        return outputData;
    }

    @JsonProperty("output_data")
    public void setOutputData(List<OutputData> outputData) {
        this.outputData = outputData;
    }

    public JobData withOutputData(List<OutputData> outputData) {
        this.outputData = outputData;
        return this;
    }

    /**
     * Job OutputData
     *
     * <a href="http://gmudcos.hopto.org/service/scale/docs/architecture/jobs/job_data.html
     *#job-data-specification-version-1-0">Source</a>
     *
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OutputData implements Serializable {

        @JsonIgnore
        private static final long serialVersionUID = 8578968687335757066L;


        @JsonProperty("name")
        private String name;

        @JsonProperty("workspace_id")
        private int workspaceId;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("workspace_id")
        public int getWorkspaceId() {
            return workspaceId;
        }

        public void setWorkspaceId(int workspaceId) {
            this.workspaceId = workspaceId;
        }

        public OutputData withName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("name", name)
                    .append("workspace_id", workspaceId)
                    .toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(name)
                    .append(workspaceId)
                    .toHashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof OutputData)) {
                return false;
            }
            OutputData rhs = (OutputData) other;
            return new EqualsBuilder()
                    .append(name, rhs.name)
                    .append(workspaceId, rhs.workspaceId)
                    .isEquals();
        }

    }


}
