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
    private List<OutputDatum> outputData;

    @JsonProperty("output_data")
    public List<OutputDatum> getOutputData() {
        return outputData;
    }

    @JsonProperty("output_data")
    public void setOutputData(List<OutputDatum> outputData) {
        this.outputData = outputData;
    }

    public JobData withOutputData(List<OutputDatum> outputData) {
        this.outputData = outputData;
        return this;
    }


}
