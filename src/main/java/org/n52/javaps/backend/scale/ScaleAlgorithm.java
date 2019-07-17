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

import java.util.Set;

import org.n52.javaps.algorithm.AbstractAlgorithm;
import org.n52.javaps.algorithm.ExecutionException;
import org.n52.javaps.description.TypedProcessDescription;
import org.n52.javaps.description.TypedProcessInputDescription;
import org.n52.javaps.description.TypedProcessOutputDescription;
import org.n52.javaps.description.impl.TypedProcessDescriptionImpl;
import org.n52.javaps.engine.ProcessExecutionContext;
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
 * TODO configure for all: jobControlOptions only async-execute dismiss
 * TODO configure for all: outputTransmission only reference
 *
 */
public class ScaleAlgorithm extends AbstractAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScaleAlgorithm.class);

    public static final String PREFIX = "scale-algorithm";

    private ScaleServiceController scaleService;

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
            String version,
            boolean storeSupported,
            boolean statusSupported) {
        super();
        this.scaleService = scaleService;
        this.scaleId = scaleId;
        description = new TypedProcessDescriptionImpl(id,
                title,
                abstrakt,
                keywords,
                metadata,
                inputs,
                outputs,
                version,
                storeSupported,
                statusSupported);
    }

    @Override
    public void execute(ProcessExecutionContext context) throws ExecutionException {
        // TODO Auto-generated method stub
        LOGGER.info("EXECUTE {}", this);
    }

    @Override
    protected TypedProcessDescription createDescription() {
        return description;
    }

}
