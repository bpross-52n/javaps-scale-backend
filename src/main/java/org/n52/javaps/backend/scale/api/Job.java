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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * See <a href="http://gmudcos.hopto.org/service/scale/docs/rest/job.html
 *#rest-job-details">REST API job details</a>.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "ended"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job extends Task implements Serializable {

    public enum Status {
        BLOCKED,
        COMPLETED,
        PENDING,
        FAILED,
    }

    @JsonIgnore
    private static final long serialVersionUID = 5414953060204559319L;

    @JsonProperty("status")
    private String status;

    @JsonProperty("ended")
    private String ended;

    @JsonProperty("outputs")
    private List<JobResultOutputs> outputs;

    public Job() {
    }

    public Job(List<Object> dependencies, String status, String ended) {
        super();
        this.status = status;
        this.ended = ended;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public Job withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCompleted() {
        return ended;
    }

    @JsonProperty("ended")
    public String getEnded() {
        return ended;
    }

    @JsonProperty("ended")
    public void setEnded(String ended) {
        this.ended = ended;
    }

    public Job withEnded(String ended) {
        this.ended = ended;
        return this;
    }
    
    @JsonProperty("outputs")
    public List<JobResultOutputs> getOutputs() {
        return outputs;
    }

    @JsonProperty("outputs")
    public void setOutputs(List<JobResultOutputs> outputs) {
        this.outputs = outputs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("compleendedted", ended)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(status)
                .append(ended)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Job)) {
            return false;
        }
        Job rhs = (Job) other;
        return new EqualsBuilder()
                .append(status, rhs.status)
                .append(ended, rhs.ended)
                .isEquals();
    }
    
    public static class JobResultOutputs implements Serializable{
        
        /**
         * 
         */
        private static final long serialVersionUID = 254071250580261272L;

        public JobResultOutputs() {}
        
        @JsonProperty("name")
        private String name;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }
        
        @JsonProperty("type")
        private String type;

        @JsonProperty("type")
        public String getType() {
            return type;
        }

        @JsonProperty("type")
        public void setType(String type) {
            this.type = type;
        }
        
        @JsonProperty("value")
        private Value value;

        @JsonProperty("value")
        public Value getValue() {
            return value;
        }

        @JsonProperty("value")
        public void setValue(Value value) {
            this.value = value;
        }
        
    }
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Value implements Serializable {
        
        /**
         * 
         */
        private static final long serialVersionUID = 1626367321684262748L;
        
        @JsonProperty("data_type")
        private List<Object> dataType = null;
        @JsonProperty("is_deleted")
        private Boolean isDeleted;
        @JsonProperty("uuid")
        private String uuid;
        @JsonProperty("created")
        private String created;
        @JsonProperty("deleted")
        private Object deleted;
        @JsonProperty("data_started")
        private Object dataStarted;
        @JsonProperty("data_ended")
        private Object dataEnded;
        @JsonProperty("source_started")
        private Object sourceStarted;
        @JsonProperty("source_ended")
        private Object sourceEnded;
        @JsonProperty("last_modified")
        private String lastModified;
        @JsonProperty("file_path")
        private String filePath;
        @JsonProperty("geometry")
        private Object geometry;
        @JsonProperty("center_point")
        private Object centerPoint;
        @JsonProperty("countries")
        private List<Object> countries = null;
        @JsonProperty("is_operational")
        private Boolean isOperational;
        @JsonProperty("is_published")
        private Boolean isPublished;
        @JsonProperty("has_been_published")
        private Boolean hasBeenPublished;
        @JsonProperty("is_superseded")
        private Boolean isSuperseded;
        @JsonProperty("published")
        private String published;
        @JsonProperty("unpublished")
        private Object unpublished;
        @JsonProperty("superseded")
        private Object superseded;
        
        @JsonProperty("meta_data")
        private MetaData metaData;
        
        @JsonProperty("job_type")
        private JobType jobType;
        @JsonProperty("job")
        private Job job;
        @JsonProperty("job_exe")
        private JobExe jobExe;
        @JsonProperty("recipe_type")
        private Object recipeType;
        @JsonProperty("recipe")
        private Object recipe;
        @JsonProperty("batch")
        private Object batch;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("job_type")
        public JobType getJobType() {
        return jobType;
        }

        @JsonProperty("job_type")
        public void setJobType(JobType jobType) {
        this.jobType = jobType;
        }

        @JsonProperty("job")
        public Job getJob() {
        return job;
        }

        @JsonProperty("job")
        public void setJob(Job job) {
        this.job = job;
        }

        @JsonProperty("job_exe")
        public JobExe getJobExe() {
        return jobExe;
        }

        @JsonProperty("job_exe")
        public void setJobExe(JobExe jobExe) {
        this.jobExe = jobExe;
        }

        @JsonProperty("recipe_type")
        public Object getRecipeType() {
        return recipeType;
        }

        @JsonProperty("recipe_type")
        public void setRecipeType(Object recipeType) {
        this.recipeType = recipeType;
        }

        @JsonProperty("recipe")
        public Object getRecipe() {
        return recipe;
        }

        @JsonProperty("recipe")
        public void setRecipe(Object recipe) {
        this.recipe = recipe;
        }

        @JsonProperty("batch")
        public Object getBatch() {
        return batch;
        }

        @JsonProperty("batch")
        public void setBatch(Object batch) {
        this.batch = batch;
        }
        
        @JsonProperty("meta_data")
        public MetaData getMetaData() {
        return metaData;
        }

        @JsonProperty("meta_data")
        public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
        }
        
        @JsonProperty("url")
        private String url;

        @JsonProperty("url")
        public String getUrl() {
            return url;
        }

        @JsonProperty("url")
        public void setUrl(String url) {
            this.url = url;
        }
        
        @JsonProperty("id")
        private String id;

        @JsonProperty("id")
        public String getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(String id) {
            this.id = id;
        }
        
        @JsonProperty("workspace")
        private Workspace workspace;

        @JsonProperty("workspace")
        public Workspace getWorkspace() {
            return workspace;
        }

        @JsonProperty("workspace")
        public void setWorkspace(Workspace workspace) {
            this.workspace = workspace;
        }
        
        @JsonProperty("file_name")
        private String fileName;

        @JsonProperty("file_name")
        public String getFileName() {
            return fileName;
        }

        @JsonProperty("file_name")
        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
        
        @JsonProperty("media_type")
        private String mediaType;

        @JsonProperty("media_type")
        public String getMediaType() {
            return mediaType;
        }

        @JsonProperty("media_type")
        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }
        
        @JsonProperty("file_type")
        private String fileType;

        @JsonProperty("file_type")
        public String getFileType() {
            return fileType;
        }

        @JsonProperty("file_type")
        public void setFileType(String fileType) {
            this.fileType = fileType;
        }       
        
        @JsonProperty("file_size")
        private String fileSize;

        @JsonProperty("file_size")
        public String getFileSize() {
            return fileSize;
        }

        @JsonProperty("file_size")
        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }
        
        @JsonProperty("data_type")
        public List<Object> getDataType() {
        return dataType;
        }

        @JsonProperty("data_type")
        public void setDataType(List<Object> dataType) {
        this.dataType = dataType;
        }

        @JsonProperty("is_deleted")
        public Boolean getIsDeleted() {
        return isDeleted;
        }

        @JsonProperty("is_deleted")
        public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        }

        @JsonProperty("uuid")
        public String getUuid() {
        return uuid;
        }

        @JsonProperty("uuid")
        public void setUuid(String uuid) {
        this.uuid = uuid;
        }

        @JsonProperty("created")
        public String getCreated() {
        return created;
        }

        @JsonProperty("created")
        public void setCreated(String created) {
        this.created = created;
        }

        @JsonProperty("deleted")
        public Object getDeleted() {
        return deleted;
        }

        @JsonProperty("deleted")
        public void setDeleted(Object deleted) {
        this.deleted = deleted;
        }

        @JsonProperty("data_started")
        public Object getDataStarted() {
        return dataStarted;
        }

        @JsonProperty("data_started")
        public void setDataStarted(Object dataStarted) {
        this.dataStarted = dataStarted;
        }

        @JsonProperty("data_ended")
        public Object getDataEnded() {
        return dataEnded;
        }

        @JsonProperty("data_ended")
        public void setDataEnded(Object dataEnded) {
        this.dataEnded = dataEnded;
        }

        @JsonProperty("source_started")
        public Object getSourceStarted() {
        return sourceStarted;
        }

        @JsonProperty("source_started")
        public void setSourceStarted(Object sourceStarted) {
        this.sourceStarted = sourceStarted;
        }

        @JsonProperty("source_ended")
        public Object getSourceEnded() {
        return sourceEnded;
        }

        @JsonProperty("source_ended")
        public void setSourceEnded(Object sourceEnded) {
        this.sourceEnded = sourceEnded;
        }

        @JsonProperty("last_modified")
        public String getLastModified() {
        return lastModified;
        }

        @JsonProperty("last_modified")
        public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
        }

        @JsonProperty("file_path")
        public String getFilePath() {
        return filePath;
        }

        @JsonProperty("file_path")
        public void setFilePath(String filePath) {
        this.filePath = filePath;
        }

        @JsonProperty("geometry")
        public Object getGeometry() {
        return geometry;
        }

        @JsonProperty("geometry")
        public void setGeometry(Object geometry) {
        this.geometry = geometry;
        }

        @JsonProperty("center_point")
        public Object getCenterPoint() {
        return centerPoint;
        }

        @JsonProperty("center_point")
        public void setCenterPoint(Object centerPoint) {
        this.centerPoint = centerPoint;
        }

        @JsonProperty("countries")
        public List<Object> getCountries() {
        return countries;
        }

        @JsonProperty("countries")
        public void setCountries(List<Object> countries) {
        this.countries = countries;
        }

        @JsonProperty("is_operational")
        public Boolean getIsOperational() {
        return isOperational;
        }

        @JsonProperty("is_operational")
        public void setIsOperational(Boolean isOperational) {
        this.isOperational = isOperational;
        }

        @JsonProperty("is_published")
        public Boolean getIsPublished() {
        return isPublished;
        }

        @JsonProperty("is_published")
        public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
        }

        @JsonProperty("has_been_published")
        public Boolean getHasBeenPublished() {
        return hasBeenPublished;
        }

        @JsonProperty("has_been_published")
        public void setHasBeenPublished(Boolean hasBeenPublished) {
        this.hasBeenPublished = hasBeenPublished;
        }

        @JsonProperty("is_superseded")
        public Boolean getIsSuperseded() {
        return isSuperseded;
        }

        @JsonProperty("is_superseded")
        public void setIsSuperseded(Boolean isSuperseded) {
        this.isSuperseded = isSuperseded;
        }

        @JsonProperty("published")
        public String getPublished() {
        return published;
        }

        @JsonProperty("published")
        public void setPublished(String published) {
        this.published = published;
        }

        @JsonProperty("unpublished")
        public Object getUnpublished() {
        return unpublished;
        }

        @JsonProperty("unpublished")
        public void setUnpublished(Object unpublished) {
        this.unpublished = unpublished;
        }

        @JsonProperty("superseded")
        public Object getSuperseded() {
        return superseded;
        }

        @JsonProperty("superseded")
        public void setSuperseded(Object superseded) {
        this.superseded = superseded;
        }
    }
    
    public static class Workspace implements Serializable {
        
        /**
         * 
         */
        private static final long serialVersionUID = -5159461797092085139L;
        
        @JsonProperty("id")
        private String id;

        @JsonProperty("id")
        public String getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(String id) {
            this.id = id;
        }
        
        @JsonProperty("name")
        private String name;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }
        
    }
    
    public static class MetaData implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 741974605014818859L;
        
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        }
    }
    
    public static class JobExe implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -5261938152163955247L;
        
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("id")
        public Integer getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(Integer id) {
            this.id = id;
        }

    }
}
