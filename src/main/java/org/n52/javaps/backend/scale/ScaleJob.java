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
package org.n52.javaps.backend.scale;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.n52.javaps.algorithm.ProcessInputs;
import org.n52.javaps.backend.scale.api.JobData;
import org.n52.javaps.backend.scale.api.JobData.OutputData;
import org.n52.javaps.backend.scale.api.QueueJob;
import org.n52.javaps.backend.scale.api.RecipeData.InputData;
import org.n52.javaps.backend.scale.api.Task;
import org.n52.javaps.backend.scale.api.util.ScaleAuthorizationFailedException;
import org.n52.javaps.description.TypedProcessInputDescription;
import org.n52.javaps.description.TypedProcessOutputDescription;
import org.n52.javaps.engine.EngineProcessExecutionContext;
import org.n52.javaps.engine.ProcessExecutionContext;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.ows.OwsKeyword;
import org.n52.shetland.ogc.ows.OwsLanguageString;
import org.n52.shetland.ogc.ows.OwsMetadata;
import org.n52.shetland.ogc.wps.OutputDefinition;

import com.google.common.base.Charsets;

public class ScaleJob extends ScaleAlgorithm {

    public ScaleJob(ScaleServiceController scaleService, int scaleId, OwsCode id, OwsLanguageString title,
            OwsLanguageString abstrakt, Set<OwsKeyword> keywords, Set<OwsMetadata> metadata,
            Set<TypedProcessInputDescription<?>> inputs, Set<TypedProcessOutputDescription<?>> outputs,
            String version) {
        super(scaleService, scaleId, id, title, abstrakt, keywords, metadata, inputs, outputs, version);
    }

    @Override
    protected Task waitForTask(int queuedTaskId) throws IOException, ScaleAuthorizationFailedException {
        return getScaleService().waitForJob(queuedTaskId);
    }

    @Override
    protected int queueTask(ProcessExecutionContext context) throws IOException, ScaleAuthorizationFailedException {
        return getScaleService().queue(convertToQueueJob(context));
    }

    private QueueJob convertToQueueJob(ProcessExecutionContext context) {

        ProcessInputs processInputs = context.getInputs();

        Collection<OutputDefinition> outputDefinitionList = Collections.emptyList();

        if (context instanceof EngineProcessExecutionContext) {
            Map<OwsCode, OutputDefinition> ouputputDefinitions =
                    ((EngineProcessExecutionContext) context).getOutputDefinitions();
            outputDefinitionList = ouputputDefinitions.values();
        }

        List<InputData> inputData = scaleService.getConverter().convertProcessInputsToInputData(processInputs);
        // TODO convert input data from context to inputdata for Recipe
        // TODO get workspaceId from context or configuration
        List<OutputData> outputData =
                scaleService.getConverter().convertProcessOutputsToOutputData(outputDefinitionList);

        QueueJob queueJob = new QueueJob().withJobTypeId(getScaleId())
                .withJobData((JobData) new JobData().withOutputData(outputData).withInputData(inputData));
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new com.fasterxml.jackson.databind.ObjectMapper().writer().writeValue(byteArrayOutputStream, queueJob);
            LOGGER.debug(byteArrayOutputStream.toString(Charsets.UTF_8));
        } catch (Exception e) {
            // ignore
            LOGGER.trace("Could not log QueueJob.", e);
        }

        return queueJob;
    }

}
