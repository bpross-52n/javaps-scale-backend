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
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "job_name",
    "is_original",
    "job"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Jobs implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 6965669396763053529L;

    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("is_original")
    private boolean isOriginal;

    @JsonProperty("job")
    private Job job;

    public Jobs() {
    }

    public Jobs(String jobName, boolean isOriginal, Job job) {
        super();
        this.jobName = jobName;
        this.isOriginal = isOriginal;
        this.job = job;
    }

    @JsonProperty("job_name")
    public String getJobName() {
        return jobName;
    }

    @JsonProperty("job_name")
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Jobs withJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    @JsonProperty("is_original")
    public boolean isIsOriginal() {
        return isOriginal;
    }

    @JsonProperty("is_original")
    public void setIsOriginal(boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public Jobs withIsOriginal(boolean isOriginal) {
        this.isOriginal = isOriginal;
        return this;
    }

    @JsonProperty("job")
    public Job getJob() {
        return job;
    }

    @JsonProperty("job")
    public void setJob(Job job) {
        this.job = job;
    }

    public Jobs withJob(Job job) {
        this.job = job;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("jobName", jobName)
                .append("isOriginal", isOriginal)
                .append("job", job)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(jobName)
                .append(isOriginal)
                .append(job)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Jobs)) {
            return false;
        }
        Jobs rhs = (Jobs) other;
        return new EqualsBuilder()
                .append(jobName, rhs.jobName)
                .append(isOriginal, rhs.isOriginal)
                .append(job, rhs.job)
                .isEquals();
    }
}
