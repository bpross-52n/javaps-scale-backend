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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.n52.javaps.algorithm.AbstractAlgorithm;
import org.n52.javaps.algorithm.ExecutionException;
import org.n52.javaps.algorithm.ProcessOutputs;
import org.n52.javaps.backend.scale.api.Job;
import org.n52.javaps.backend.scale.api.Job.JobResultOutputs;
import org.n52.javaps.backend.scale.api.Task;
import org.n52.javaps.backend.scale.api.util.ScaleAuthorizationFailedException;
import org.n52.javaps.description.TypedProcessDescription;
import org.n52.javaps.description.TypedProcessInputDescription;
import org.n52.javaps.description.TypedProcessOutputDescription;
import org.n52.javaps.description.impl.TypedProcessDescriptionImpl;
import org.n52.javaps.engine.EngineProcessExecutionContext;
import org.n52.javaps.engine.ProcessExecutionContext;
import org.n52.javaps.io.GenericFileData;
import org.n52.javaps.io.data.binding.complex.GenericFileDataBinding;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.ows.OwsKeyword;
import org.n52.shetland.ogc.ows.OwsLanguageString;
import org.n52.shetland.ogc.ows.OwsMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 *
 */
public abstract class ScaleAlgorithm extends AbstractAlgorithm {

    public static final String PREFIX = "scale-algorithm";

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected ScaleServiceController scaleService;

    private TypedProcessDescriptionImpl description;

    private int scaleId;

    public ScaleAlgorithm(ScaleServiceController scaleService,
            int scaleId,
            OwsCode id,
            OwsLanguageString title,
            OwsLanguageString abstrakt,
            Set<OwsKeyword> keywords,
            Set<OwsMetadata> metadata,
            Set<TypedProcessInputDescription<?>> inputs,
            Set<TypedProcessOutputDescription<?>> outputs,
            String version) {
        super();
        this.scaleService = scaleService;
        this.scaleId = scaleId;
        // FIXME switch to builder pattern
        description = new TypedProcessDescriptionImpl(id,
                title,
                abstrakt,
                keywords,
                metadata,
                inputs,
                outputs,
                version,
                true,
                true);
    }

    @Override
    public void execute(ProcessExecutionContext context) throws ExecutionException {
        LOGGER.info("EXECUTE {} - {}", this, context.getJobId().getValue());
        // TODO Prepare Workspace
        // might not be required. We might use prepared workspaces for in- and output
        // http://gmudcos.hopto.org/service/scale/#/workspaces/2 workspace-output
        // TODO Prepare Inputs - only property inputs can be provided (from WPS as LiteralInputs)
        // might not be required.
        // TODO Queue Job
        // convert context to queuejob
        try {
            // send queuejob to scale
            int queuedTaskId = queueTask(context);
            if (queuedTaskId < 1) {
                throw new ExecutionException();
            }
            // WAIT for scale job to finish
            // Regularly check if job is finished via job status interface
            Task result = waitForTask(queuedTaskId);

            if (result instanceof Job) {
                LOGGER.info("Result is Job.");
                
                Job resultJob = (Job)result;
                
                List<JobResultOutputs> outputs = resultJob.getOutputs();
                
                ProcessOutputs processOutputs = context.getOutputs();
                
                scaleService.getConverter().convertJobResultsOutputsToProcessOutputs(outputs, processOutputs);                
                
            }

            // TODO continue development here
            // store results in context
            // What about files?
            // Can the scale urls be retrieved?
            // Or
            // I have to download and then save in GenericFileDataBinding
            // and save in the context
            
        } catch (IOException | ScaleAuthorizationFailedException e) {
            throw new ExecutionException(e);
        }
        LOGGER.info("EXECUTE {} - {} FINISHED", this, context.getJobId().getValue());
    }

    protected abstract Task waitForTask(int queuedTaskId) throws IOException, ScaleAuthorizationFailedException;

    protected abstract int queueTask(ProcessExecutionContext context)
            throws IOException, ScaleAuthorizationFailedException;

    @Override
    protected TypedProcessDescription createDescription() {
        return description;
    }

    protected ScaleServiceController getScaleService() {
        return scaleService;
    }

    protected Integer getScaleId() {
        return scaleId;
    }

}
