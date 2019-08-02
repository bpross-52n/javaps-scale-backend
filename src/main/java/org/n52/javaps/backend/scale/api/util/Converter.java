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

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.n52.javaps.backend.scale.ScaleAlgorithm;
import org.n52.javaps.backend.scale.ScaleJob;
import org.n52.javaps.backend.scale.ScaleRecipe;
import org.n52.javaps.backend.scale.ScaleServiceController;
import org.n52.javaps.backend.scale.api.InputDatum;
import org.n52.javaps.backend.scale.api.InputDatumFile;
import org.n52.javaps.backend.scale.api.JobType;
import org.n52.javaps.backend.scale.api.OutputDatum;
import org.n52.javaps.backend.scale.api.RecipeType;
import org.n52.javaps.backend.scale.api.TaskType;
import org.n52.javaps.description.TypedProcessInputDescription;
import org.n52.javaps.description.TypedProcessOutputDescription;
import org.n52.javaps.description.impl.TypedComplexInputDescriptionImpl;
import org.n52.javaps.description.impl.TypedLiteralInputDescriptionImpl;
import org.n52.javaps.io.data.binding.complex.GenericFileDataBinding;
import org.n52.javaps.io.literal.xsd.LiteralStringType;
import org.n52.shetland.ogc.ows.OwsAnyValue;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.ows.OwsDomainMetadata;
import org.n52.shetland.ogc.ows.OwsKeyword;
import org.n52.shetland.ogc.ows.OwsLanguageString;
import org.n52.shetland.ogc.ows.OwsMetadata;
import org.n52.shetland.ogc.wps.Format;
import org.n52.shetland.ogc.wps.InputOccurence;
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
        Set<TypedProcessOutputDescription<?>> outputs =
                convertToOutputDescriptions(jobType.getInterface().getOutputData());
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
        return Collections.emptySet();
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
                Format defaultFormat = supportedFormats.iterator().next();
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

}