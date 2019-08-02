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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Implementation of <a href="http://gmudcos.hopto.org/service/scale/docs/architecture/jobs/recipe_data.html
 *#recipe-data-specification-version-1-0">Recipe Data Specification Version 1.0</a>.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 * @see TaskData
 * @see JobData
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "version",
        "input_data",
        "workspace_id"
})
public class RecipeData extends TaskData implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = -8307470273788163750L;

    @JsonProperty("workspace_id")
    private Integer workspaceId;

    @JsonProperty("workspace_id")
    public Integer getWorkspaceId() {
        return workspaceId;
    }

    @JsonProperty("workspace_id")
    public void setWorkspaceId(Integer workspaceId) {
        this.workspaceId = workspaceId;
    }

    public RecipeData withWorkspaceId(Integer workspaceId) {
        this.workspaceId = workspaceId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("workspaceId", workspaceId)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(workspaceId)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RecipeData)) {
            return false;
        }
        RecipeData rhs = (RecipeData) other;
        return new EqualsBuilder()
                .append(workspaceId, rhs.workspaceId)
                .isEquals();
    }

    /**
     * This types maps three different types that cannot be distinguished
     * because no type or similar property is available.
     *
     * The three types are the following:
     *
     * <ul>
     * <li>LiteralInputDatum:
     *  <ul><li><code>name</code>: String</li><li><code>value</code>: String</li></ul>
     * </li>
     * <li>FileInputDatum:
     *  <ul><li><code>name</code>: String</li><li><code>file_id</code>: Integer</li></ul>
     * </li>
     * <li>FilesInputDatum:
     *  <ul><li><code>name</code>: String</li><li><code>file_ids</code>: List&lt;Integer></li></ul>
     * </ul>
     *
     * <a href="http://gmudcos.hopto.org/service/scale/docs/architecture/jobs/job_data.html
     *#job-data-specification-version-1-0">Source</a>
     *
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InputData implements Serializable {

        @JsonIgnore
        private static final long serialVersionUID = 6747834919867377371L;

        @JsonProperty("name")
        private String name;

        @JsonProperty("value")
        private String value;

        @JsonProperty("file_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer fileId;

        @JsonProperty("file_ids")
        @JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
        private List<Integer> fileIds;

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        public InputData withName(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("value")
        public String getValue() {
            return value;
        }

        @JsonProperty("value")
        public void setValue(String value) {
            this.value = value;
        }

        public InputData withValue(String value) {
            this.value = value;
            return this;
        }

        @JsonProperty("file_id")
        public Integer getFileId() {
            return fileId;
        }

        @JsonProperty("file_id")
        public void setFileId(Integer fileId) {
            this.fileId = fileId;
        }

        public InputData withFileId(Integer fileId) {
            this.fileId = fileId;
            return this;
        }

        @JsonProperty("file_ids")
        public List<Integer> getFileIds() {
            return fileIds;
        }

        @JsonProperty("file_ids")
        public void setFileIds(List<Integer> fileIds) {
            this.fileIds = fileIds;
        }

        public InputData withFileIds(List<Integer> fileIds) {
            this.fileIds = fileIds;
            return this;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("name", name)
                    .append("value", value)
                    .append("fileId", fileId)
                    .append("fileIds", fileIds)
                    .toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(fileIds)
                    .append(fileId)
                    .append(name)
                    .append(value)
                    .toHashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof InputData)) {
                return false;
            }
            InputData rhs = (InputData) other;
            return new EqualsBuilder()
                    .append(fileIds, rhs.fileIds)
                    .append(fileId, rhs.fileId)
                    .append(name, rhs.name)
                    .append(value, rhs.value)
                    .isEquals();
        }

    }

}
