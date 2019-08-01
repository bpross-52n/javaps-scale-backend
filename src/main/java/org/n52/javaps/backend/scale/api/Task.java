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

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Task {

    @JsonProperty("id")
    protected int id;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("version")
    protected String version;

    @JsonProperty("title")
    protected String title;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("is_system")
    protected boolean isSystem;

    @JsonProperty("is_active")
    protected boolean isActive;

    @JsonProperty("revision_num")
    protected int revision;

    @JsonProperty("created")
    protected String created;

    @JsonProperty("last_modified")
    protected String lastModified;

    @JsonProperty("archived")
    protected String archived;

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public Task withId(int id) {
        this.id = id;
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

    public Task withName(String name) {
        this.name = name;
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

    public Task withVersion(String version) {
        this.version = version;
        return this;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Task withTitle(String title) {
        this.title = title;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Task withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("is_system")
    public boolean isIsSystem() {
        return isSystem;
    }

    @JsonProperty("is_system")
    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public Task withIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
        return this;
    }

    @JsonProperty("is_active")
    public boolean isIsActive() {
        return isActive;
    }

    @JsonProperty("is_active")
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Task withIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    @JsonProperty("revision_num")
    public int getRevision() {
        return revision;
    }

    @JsonProperty("revision_num")
    public void setRevision(int revision) {
        this.revision = revision;
    }

    public Task withRevision(int revision) {
        this.revision = revision;
        return this;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    public Task withCreated(String created) {
        this.created = created;
        return this;
    }

    @JsonProperty("last_modified")
    public String getLastModified() {
        return lastModified;
    }

    @JsonProperty("last_modified")
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Task withLastModified(String lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    @JsonProperty("archived")
    public String getArchived() {
        return archived;
    }

    @JsonProperty("archived")
    public void setArchived(String archived) {
        this.archived = archived;
    }

    public Task withArchived(String archived) {
        this.archived = archived;
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
                .append(lastModified)
                .append(revision)
                .append(isSystem)
                .append(version)
                .append(isActive)
                .append(id)
                .append(title)
                .append(archived)
                .append(created)
                .append(description)
                .append(name)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Task)) {
            return false;
        }
        Task rhs = (Task) other;
        return new EqualsBuilder()
                .append(lastModified, rhs.lastModified)
                .append(revision, rhs.revision)
                .append(isSystem, rhs.isSystem)
                .append(version, rhs.version)
                .append(isActive, rhs.isActive)
                .append(id, rhs.id)
                .append(title, rhs.title)
                .append(archived, rhs.archived)
                .append(created, rhs.created)
                .append(description, rhs.description)
                .append(name, rhs.name)
                .isEquals();
    }

}