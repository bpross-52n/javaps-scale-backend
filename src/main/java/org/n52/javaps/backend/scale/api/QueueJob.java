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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * See <a href="http://gmudcos.hopto.org/service/scale/docs/rest/queue.html">
 * REST API Queue Services: Queue New Job</a>.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 * @see QueueRecipe
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "job_type_id",
    "job_data"
})
public class QueueJob implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = -9164919847349950376L;

    @JsonProperty("job_type_id")
    private Integer jobTypeId;

    @JsonProperty("job_data")
    private JobData jobData;

    public QueueJob withJobTypeId(Integer jobTypeId) {
        this.jobTypeId = jobTypeId;
        return this;
    }

    @JsonProperty("job_type_id")
    public Integer getJobTypeId() {
        return jobTypeId;
    }

    @JsonProperty("job_type_id")
    public void setJobTypeId(Integer jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public QueueJob withJobData(JobData jobData) {
        this.jobData = jobData;
        return this;
    }

    @JsonProperty("job_data")
    public JobData getJobData() {
        return jobData;
    }

    @JsonProperty("job_data")
    public void setJobData(JobData jobData) {
        this.jobData = jobData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("jobTypeId", jobTypeId)
                .append("jobData", jobData)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(jobTypeId)
                .append(jobData)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof QueueJob)) {
            return false;
        }
        QueueJob rhs = (QueueJob) other;
        return new EqualsBuilder()
                .append(jobTypeId, rhs.jobTypeId)
                .append(jobData, rhs.jobData)
                .isEquals();
    }


}
