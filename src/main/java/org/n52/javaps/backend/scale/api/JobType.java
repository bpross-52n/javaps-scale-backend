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

/**
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
public class JobType {

    private int id;
    private String name;
    private String version;
    private String title;
    private String description;
    private String category;
    private String author_name;
    private String author_url;
    private boolean is_system;
    private boolean is_long_running;
    private boolean is_active;
    private boolean is_operational;
    private boolean is_paused;
    private String icon_code;
    private boolean uses_docker;
    private boolean docker_privileged;
    private String docker_image;
    private int revision_num;
    private int priority;
    private int timeout;
    private int max_scheduled;
    private int max_tries;
    private double cpus_required;
    private double mem_required;
    private double mem_const_required;
    private double mem_mult_required;
    private double shared_mem_required;
    private double disk_out_const_required;
    private double disk_out_mult_required;
    // TODO is this working
    private String created;
    private String archived;
    private String paused;
    private String last_modified;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getAuthor_name() {
        return author_name;
    }
    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }
    public String getAuthor_url() {
        return author_url;
    }
    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }
    public boolean isIs_system() {
        return is_system;
    }
    public void setIs_system(boolean is_system) {
        this.is_system = is_system;
    }
    public boolean isIs_long_running() {
        return is_long_running;
    }
    public void setIs_long_running(boolean is_long_running) {
        this.is_long_running = is_long_running;
    }
    public boolean isIs_active() {
        return is_active;
    }
    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
    public boolean isIs_operational() {
        return is_operational;
    }
    public void setIs_operational(boolean is_operational) {
        this.is_operational = is_operational;
    }
    public boolean isIs_paused() {
        return is_paused;
    }
    public void setIs_paused(boolean is_paused) {
        this.is_paused = is_paused;
    }
    public String getIcon_code() {
        return icon_code;
    }
    public void setIcon_code(String icon_code) {
        this.icon_code = icon_code;
    }
    public boolean isUses_docker() {
        return uses_docker;
    }
    public void setUses_docker(boolean uses_docker) {
        this.uses_docker = uses_docker;
    }
    public boolean isDocker_privileged() {
        return docker_privileged;
    }
    public void setDocker_privileged(boolean docker_privileged) {
        this.docker_privileged = docker_privileged;
    }
    public String getDocker_image() {
        return docker_image;
    }
    public void setDocker_image(String docker_image) {
        this.docker_image = docker_image;
    }
    public int getRevision_num() {
        return revision_num;
    }
    public void setRevision_num(int revision_num) {
        this.revision_num = revision_num;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getTimeout() {
        return timeout;
    }
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    public int getMax_scheduled() {
        return max_scheduled;
    }
    public void setMax_scheduled(int max_scheduled) {
        this.max_scheduled = max_scheduled;
    }
    public int getMax_tries() {
        return max_tries;
    }
    public void setMax_tries(int max_tries) {
        this.max_tries = max_tries;
    }
    public double getCpus_required() {
        return cpus_required;
    }
    public void setCpus_required(double cpus_required) {
        this.cpus_required = cpus_required;
    }
    public double getMem_required() {
        return mem_required;
    }
    public void setMem_required(double mem_required) {
        this.mem_required = mem_required;
    }
    public double getMem_const_required() {
        return mem_const_required;
    }
    public void setMem_const_required(double mem_const_required) {
        this.mem_const_required = mem_const_required;
    }
    public double getMem_mult_required() {
        return mem_mult_required;
    }
    public void setMem_mult_required(double mem_mult_required) {
        this.mem_mult_required = mem_mult_required;
    }
    public double getShared_mem_required() {
        return shared_mem_required;
    }
    public void setShared_mem_required(double shared_mem_required) {
        this.shared_mem_required = shared_mem_required;
    }
    public double getDisk_out_const_required() {
        return disk_out_const_required;
    }
    public void setDisk_out_const_required(double disk_out_const_required) {
        this.disk_out_const_required = disk_out_const_required;
    }
    public double getDisk_out_mult_required() {
        return disk_out_mult_required;
    }
    public void setDisk_out_mult_required(double disk_out_mult_required) {
        this.disk_out_mult_required = disk_out_mult_required;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }
    public String getArchived() {
        return archived;
    }
    public void setArchived(String archived) {
        this.archived = archived;
    }
    public String getPaused() {
        return paused;
    }
    public void setPaused(String paused) {
        this.paused = paused;
    }
    public String getLast_modified() {
        return last_modified;
    }
    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }
}
