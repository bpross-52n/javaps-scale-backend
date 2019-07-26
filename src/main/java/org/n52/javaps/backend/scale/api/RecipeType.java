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
    "id",
    "name",
    "version",
    "title",
    "description",
    "is_system",
    "is_active",
    "definition",
    "revision_num",
    "created",
    "last_modified",
    "archived"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeType implements Serializable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("version")
    private String version;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("is_system")
    private boolean isSystem;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("definition")
    private Definition definition;

    @JsonProperty("revision_num")
    private int revision;

    @JsonProperty("created")
    private String created;

    @JsonProperty("last_modified")
    private String lastModified;

    @JsonProperty("archived")
    private String archived;

    @JsonIgnore
    private final static long serialVersionUID = -3294460028265008915L;

    public RecipeType() {
    }

    public RecipeType(int id, String name, String version, String title, String description, boolean isSystem,
            boolean isActive, Definition definition, int revision, String created, String lastModified, String archived) {
        super();
        this.id = id;
        this.name = name;
        this.version = version;
        this.title = title;
        this.description = description;
        this.isSystem = isSystem;
        this.isActive = isActive;
        this.definition = definition;
        this.revision = revision;
        this.created = created;
        this.lastModified = lastModified;
        this.archived = archived;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public RecipeType withId(int id) {
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

    public RecipeType withName(String name) {
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

    public RecipeType withVersion(String version) {
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

    public RecipeType withTitle(String title) {
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

    public RecipeType withDescription(String description) {
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

    public RecipeType withIsSystem(boolean isSystem) {
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

    public RecipeType withIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    @JsonProperty("definition")
    public Definition getDefinition() {
        return definition;
    }

    @JsonProperty("definition")
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    public RecipeType withDefinition(Definition definition) {
        this.definition = definition;
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

    public RecipeType withRevision(int revision) {
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

    public RecipeType withCreated(String created) {
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

    public RecipeType withLastModified(String lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    @JsonProperty("archived")
    public Object getArchived() {
        return archived;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).append("version", version).append("title", title).append("description", description).append("isSystem", isSystem).append("isActive", isActive).append("revision", revision).append("created", created).append("lastModified", lastModified).append("archived", archived).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(lastModified).append(revision).append(isSystem).append(version).append(isActive).append(id).append(title).append(archived).append(created).append(description).append(name).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof RecipeType == false) {
            return false;
        }
        RecipeType rhs = (RecipeType) other;
        return new EqualsBuilder().append(lastModified, rhs.lastModified).append(revision, rhs.revision).append(isSystem, rhs.isSystem).append(version, rhs.version).append(isActive, rhs.isActive).append(id, rhs.id).append(title, rhs.title).append(archived, rhs.archived).append(created, rhs.created).append(description, rhs.description).append(name, rhs.name).isEquals();
    }

}
