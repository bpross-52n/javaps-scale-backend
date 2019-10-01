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
package org.n52.javaps.backend.scale.api.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.n52.javaps.algorithm.ProcessInputs;
import org.n52.javaps.backend.scale.ScaleAlgorithm;
import org.n52.javaps.backend.scale.ScaleJob;
import org.n52.javaps.backend.scale.ScaleRecipe;
import org.n52.javaps.backend.scale.ScaleServiceController;
import org.n52.javaps.backend.scale.api.InputDatum;
import org.n52.javaps.backend.scale.api.InputDatumFile;
import org.n52.javaps.backend.scale.api.JobData.OutputData;
import org.n52.javaps.backend.scale.api.JobType;
import org.n52.javaps.backend.scale.api.OutputDatum;
import org.n52.javaps.backend.scale.api.RecipeData.InputData;
import org.n52.javaps.backend.scale.api.RecipeType;
import org.n52.javaps.backend.scale.api.TaskType;
import org.n52.javaps.backend.scale.api.Job.JobResultOutputs;
import org.n52.javaps.description.TypedProcessInputDescription;
import org.n52.javaps.description.TypedProcessOutputDescription;
import org.n52.javaps.description.impl.TypedComplexInputDescriptionImpl;
import org.n52.javaps.description.impl.TypedComplexOutputDescriptionImpl;
import org.n52.javaps.description.impl.TypedLiteralInputDescriptionImpl;
import org.n52.javaps.description.impl.TypedLiteralOutputDescriptionImpl;
import org.n52.javaps.io.Data;
import org.n52.javaps.io.GenericFileData;
import org.n52.javaps.io.complex.ComplexData;
import org.n52.javaps.io.data.binding.complex.GenericFileDataBinding;
import org.n52.javaps.io.literal.LiteralData;
import org.n52.javaps.io.literal.xsd.LiteralStringType;
import org.n52.shetland.ogc.ows.OwsAnyValue;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.ows.OwsDomainMetadata;
import org.n52.shetland.ogc.ows.OwsKeyword;
import org.n52.shetland.ogc.ows.OwsLanguageString;
import org.n52.shetland.ogc.ows.OwsMetadata;
import org.n52.shetland.ogc.wps.Format;
import org.n52.shetland.ogc.wps.InputOccurence;
import org.n52.shetland.ogc.wps.OutputDefinition;
import org.n52.shetland.ogc.wps.description.impl.LiteralDataDomainImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts the JSON jackson POJOs to javaPS based objects:
 *
 * <ul>
 * <li> {@link RecipeType} &rarr; {@link ScaleRecipe} </li>
 * <li> {@link JobType} &rarr; {@link ScaleJob} </li>
 * </ul>
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 * @see ScaleAlgorithm
 * @see ScaleRecipe
 * @see ScaleJob
 * @see RecipeType
 * @see JobType
 */
public class Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);

    private ScaleServiceController scaleService;

    public Converter(ScaleServiceController scaleService) {
        this.scaleService = scaleService;
    }

    public ScaleAlgorithm convertToAlgorithm(RecipeType recipeType) {
        LOGGER.trace("Converting RecipeType '{}' to ScaleAlgorithm", recipeType);

        // TODO better handling because of potential null values
        Set<TypedProcessInputDescription<?>> inputs = convertToInputDescriptions(
                recipeType.getDefinition().getInputData());
        Set<TypedProcessOutputDescription<?>> outputs = convertToOutputDescriptions(
                recipeType.getDefinition().getOutputData());
        return new ScaleRecipe(scaleService,
                recipeType.getId(),
                createId(recipeType),
                createTitle(recipeType),
                createAbstract(recipeType),
                Collections.emptySet(),
                Collections.emptySet(),
                inputs,
                outputs,
                createVersion(recipeType));
    }

    public ScaleAlgorithm convertToAlgorithm(JobType jobType) {
        LOGGER.trace("Converting JobType '{}' to ScaleAlgorithm", jobType);

        // TODO map JSON property that could be converted to input descriptions
        Set<TypedProcessInputDescription<?>> inputs =
                convertToInputDescriptions(jobType.getInterface().getInputData());

        if(inputs.contains(null)) {
            LOGGER.info("Skipping scale job: " + jobType);
            return null;
        }

        Set<TypedProcessOutputDescription<?>> outputs =
                convertToOutputDescriptions(jobType.getInterface().getOutputData());

        if(outputs.contains(null)) {
            LOGGER.info("Skipping scale job: " + jobType);
            return null;
        }

        return new ScaleJob(scaleService,
                jobType.getId(),
                createId(jobType),
                createTitle(jobType),
                createAbstract(jobType),
                Collections.emptySet(),
                Collections.emptySet(),
                inputs,
                outputs,
                createVersion(jobType));
    }

    public List<InputData> convertProcessInputsToInputData(ProcessInputs processInputs) {

        List<InputData> result = new ArrayList<InputData>();

        for (Entry<OwsCode, List<Data<?>>> entry : processInputs.entrySet()) {

            OwsCode id = entry.getKey();

            List<Data<?>> dataList = processInputs.get(id);

            //size should be always one
            Data<?> data = dataList.get(0);

            //create new InputData
            InputData inputData = new InputData();

            if (data instanceof LiteralData) {

                LiteralData literalData = (LiteralData) data;

                Object value = literalData.getPayload();

                inputData.setName(id.getValue());

                inputData.setValue(value.toString());

            } else if (data instanceof ComplexData) {

                LOGGER.info("ComplexData input not added to JobData: " + id.getValue());
            }

            result.add(inputData);
        }

        return result;
    }

    public List<OutputData> convertProcessOutputsToOutputData(Collection<OutputDefinition> outputDefinitionList) {

        List<OutputData> result = new ArrayList<OutputData>();

        for (OutputDefinition outputDefinition : outputDefinitionList) {

            OwsCode id = outputDefinition.getId();

            //create new InputData
            OutputData outputData = new OutputData();

            outputData.setName(id.getValue());

            outputData.setWorkspaceId(scaleService.getConfiguration().getScaleOutputWorkspaceId());

//            if (outputDefinition instanceof LiteralOutputDescription) {
//
//                LOGGER.info("LiteralData output not added to JobData: " + id.getValue());
//
//            } else if (data instanceof ComplexData) {
//
//                outputData.setWorkspaceId(scaleService.getConfiguration().getScaleOutputWorkspaceId());
//            }

            result.add(outputData);
        }

        return result;
    }

    private OwsLanguageString createAbstract(TaskType taskType) {
        OwsLanguageString abstrakt = new OwsLanguageString(taskType.getDescription());
        LOGGER.trace("Created abstrakt: '{}'", abstrakt);
        return abstrakt;
    }

    private OwsCode createId(TaskType taskType) {
        return new OwsCode(String.format("%s-%s-%s-r%s",
                ScaleAlgorithm.PREFIX,
                taskType.getName(),
                taskType.getVersion(),
                taskType.getRevision()));
    }

    private OwsLanguageString createTitle(TaskType taskType) {
        OwsLanguageString title = new OwsLanguageString(taskType.getTitle());
        LOGGER.trace("Created title: '{}'", title);
        return title;
    }

    private String createVersion(TaskType taskType) {
        return String.format("%s-r%s",
                taskType.getVersion(),
                taskType.getRevision());
    }

    private Set<TypedProcessOutputDescription<?>> convertToOutputDescriptions(List<OutputDatum> outputData) {
        // TODO Auto-generated method stub -> implement me!
        if (outputData == null || outputData.isEmpty()) {
            return Collections.emptySet();
        }
        return outputData.stream().map(this::convertToOutputDescription).collect(Collectors.toSet());
    }

    private Set<TypedProcessInputDescription<?>> convertToInputDescriptions(List<InputDatum> inputData) {
        if (inputData == null || inputData.isEmpty()) {
            return Collections.emptySet();
        }
        return inputData.stream().map(this::convertToInputDescription).collect(Collectors.toSet());
    }

    private TypedProcessInputDescription<?> convertToInputDescription(InputDatum inputData) {
        OwsCode id = new OwsCode(inputData.getName());
        OwsLanguageString title = new OwsLanguageString(inputData.getName());
        OwsLanguageString abstrakt = title;
        Set<OwsKeyword> keywords = Collections.emptySet();
        Set<OwsMetadata> metadata = Collections.emptySet();
        InputOccurence occurence = new InputOccurence(BigInteger.valueOf(1), BigInteger.valueOf(1));
        switch (inputData.getClass().getSimpleName()) {
            case "InputDatumFile":
                BigInteger maximumMegabytes = BigInteger.ZERO;
                Set<Format> supportedFormats = ((InputDatumFile) inputData).getMediaTypes().stream()
                        .map(m -> {
                            return new Format(m);
                        })
                        .collect(Collectors.toSet());
                Format defaultFormat = null;
                try {
                    defaultFormat = supportedFormats.iterator().next();
                } catch (Exception e) {
                    return null;
                }
                // FIXME switch to builder pattern
                return new TypedComplexInputDescriptionImpl(id,
                        title, abstrakt, keywords, metadata, occurence, defaultFormat, supportedFormats,
                        maximumMegabytes, GenericFileDataBinding.class);
            case "InputDatumProperty":
            default:
                // FIXME switch to builder pattern
                return new TypedLiteralInputDescriptionImpl(
                        id,
                        title,
                        abstrakt,
                        keywords,
                        metadata,
                        occurence,
                        new LiteralDataDomainImpl(OwsAnyValue.instance(),
                                // datatype
                                null,
                                new OwsDomainMetadata(" "),
                                // default value
                                null),
                        Collections.emptySet(),
                        new LiteralStringType());
        }
    }

    private TypedProcessOutputDescription<?> convertToOutputDescription(OutputDatum outputData) {
        OwsCode id = new OwsCode(outputData.getName());
        OwsLanguageString title = new OwsLanguageString(outputData.getName());
        OwsLanguageString abstrakt = title;
        Set<OwsKeyword> keywords = Collections.emptySet();
        Set<OwsMetadata> metadata = Collections.emptySet();
        //TODO: check switch statement
        switch (outputData.getClass().getSimpleName()) {
        case "OutputDatum":

            Collection<Format> formatList = new ArrayList<Format>();

            String mediaType = outputData.getMediaType();
            if (mediaType != null && !mediaType.isEmpty()) {
                formatList.add(new Format(outputData.getMediaType()));
                formatList.add(new Format(outputData.getMediaType(), "base64"));
            }
            formatList.add(new Format("application/octet-stream"));
            formatList.add(new Format("application/octet-stream", "base64"));

            Set<Format> supportedFormats = new HashSet<Format>(formatList);
//            Set<Format> supportedFormats = Collections.singleton(new Format(outputData.getMediaType()));

            Format defaultFormat = supportedFormats.iterator().next();
            // FIXME switch to builder pattern
            return new TypedComplexOutputDescriptionImpl(id,
                    title, abstrakt, keywords, metadata, defaultFormat, supportedFormats,
                    null, GenericFileDataBinding.class);
        case "OutputDatumProperty":
        default:
            // FIXME switch to builder pattern
            return new TypedLiteralOutputDescriptionImpl(
                    id,
                    title,
                    abstrakt,
                    keywords,
                    metadata,
                    new LiteralDataDomainImpl(OwsAnyValue.instance(),
                            // datatype
                            null,
                            new OwsDomainMetadata(" "),
                            // default value
                            null),
                    Collections.emptySet(),
                    new LiteralStringType());
        }
    }

    public void convertJobResultsOutputsToProcessOutputs(List<JobResultOutputs> jobResultOutputs,
            HashMap<OwsCode, Data<?>> processOutputs) {

        for (JobResultOutputs jobResultOutput : jobResultOutputs) {

            OwsCode id = new OwsCode(jobResultOutput.getName());

            String type = jobResultOutput.getType();

            switch (type) {
            case "file":
                Data<?> processOutput = convertJobResultFileOutputToProcessOutput(jobResultOutput);
                processOutputs.put(id, processOutput);
                break;

            default:
                break;
            }

        }

    }

    private Data<?> convertJobResultFileOutputToProcessOutput(JobResultOutputs jobResultOutput) {

        String urlString = jobResultOutput.getValue().getUrl();

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            LOGGER.error("Exception while creating URL from String: " + urlString, e.getMessage());
            return null;
        }

        File resultFile = null;
        try {
            resultFile = File.createTempFile("wps", ".dat");
        } catch (IOException e) {
            LOGGER.error("Exception while trying to create temp file.", e.getMessage());
            return null;
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(resultFile);
        } catch (FileNotFoundException e) {
            LOGGER.error("Exception while trying to create FileOutputStream for temp file.", e.getMessage());
            return null;
        }

        try {
            IOUtils.copy(url.openStream(), fileOutputStream);
        } catch (IOException e) {
            LOGGER.error("Exception while trying to copy streams.", e.getMessage());
            return null;
        }

        String mimeType = jobResultOutput.getValue().getMediaType();

        try {
             return new GenericFileDataBinding(new GenericFileData(resultFile, mimeType));
        } catch (IOException e) {
            LOGGER.error("Exception while trying to create GenericFileDataBinding.", e.getMessage());
        }
        return null;

    }

}
