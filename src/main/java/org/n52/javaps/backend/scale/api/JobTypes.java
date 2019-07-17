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

/**
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
public class JobTypes {

    private int count;
    private int next;
    private int previous;
    private List<JobType> results;

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getNext() {
        return next;
    }
    public void setNext(int next) {
        this.next = next;
    }
    public int getPrevious() {
        return previous;
    }
    public void setPrevious(int previous) {
        this.previous = previous;
    }
    public List<JobType> getResults() {
        return results;
    }
    public void setResults(List<JobType> results) {
        this.results = results;
    }

}
