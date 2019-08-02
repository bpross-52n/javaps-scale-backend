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
 * See  <a href="http://gmudcos.hopto.org/service/scale/docs/rest/job_type.html
 *#rest-job-type-details">REST API Job Type Services: Job Type Details</a>.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 * @see Interface
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "version",
    "title",
    "description",
    "is_system",
    "is_active",
    "revision_num",
    "created",
    "archived",
    "last_modified",
    "interface"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobType extends TaskType implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = -3294460028265008915L;

    @JsonProperty("interface")
    private Interface interFace;

    public JobType() {
    }

    public JobType(int id, String name, String version, String title, String description, boolean isSystem,
            boolean isActive, int revision, String created, String lastModified, String archived, Interface interFace) {
        super();
        this.id = id;
        this.name = name;
        this.version = version;
        this.title = title;
        this.description = description;
        this.isSystem = isSystem;
        this.isActive = isActive;
        this.revision = revision;
        this.created = created;
        this.lastModified = lastModified;
        this.archived = archived;
        this.interFace = interFace;
    }

    @JsonProperty("interface")
    public Interface getInterface() {
        return interFace;
    }

    @JsonProperty("interface")
    public void setInterface(Interface interFace) {
        this.interFace = interFace;
    }

    public JobType withInterface(Interface interFace) {
        this.interFace = interFace;
        return this;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("version", version)
                .append("title", title)
                .append("description", description)
                .append("isSystem", isSystem)
                .append("isActive", isActive)
                .append("revision", revision)
                .append("created", created)
                .append("lastModified", lastModified)
                .append("archived", archived)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(interFace)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof JobType)) {
            return false;
        }
        JobType rhs = (JobType) other;
        return new EqualsBuilder()
                .append(interFace, rhs.interFace)
                .isEquals();
    }

}
