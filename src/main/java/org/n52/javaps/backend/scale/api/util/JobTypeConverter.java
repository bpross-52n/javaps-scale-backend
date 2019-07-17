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

import java.util.Collections;
import java.util.Set;

import org.n52.javaps.backend.scale.ScaleAlgorithm;
import org.n52.javaps.backend.scale.ScaleServiceController;
import org.n52.javaps.backend.scale.api.JobType;
import org.n52.javaps.description.TypedProcessInputDescription;
import org.n52.javaps.description.TypedProcessOutputDescription;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.ows.OwsKeyword;
import org.n52.shetland.ogc.ows.OwsLanguageString;
import org.n52.shetland.ogc.ows.OwsMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
public class JobTypeConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobTypeConverter.class);

    private ScaleServiceController scaleService;

    public JobTypeConverter(ScaleServiceController scaleService) {
        this.scaleService = scaleService;
    }

    public ScaleAlgorithm convertToAlgorithm(JobType jt) {
        LOGGER.trace("Converting JobType '{}' to ScaleAlgorithm", jt);

        OwsCode id = new OwsCode(String.format("%s-%s-v%s-r%s",
                ScaleAlgorithm.PREFIX,
                jt.getName(),
                jt.getVersion(),
                jt.getRevision_num()));

        // TODO better handling because of potential null values

        OwsLanguageString title = new OwsLanguageString(jt.getTitle());
        LOGGER.trace("Created title: '{}'", title);

        OwsLanguageString abstrakt = new OwsLanguageString(jt.getDescription());
        LOGGER.trace("Created abstrakt: '{}'", abstrakt);

        Set<OwsKeyword> keywords = Sets.newHashSet(new OwsKeyword(jt.getCategory()));
        LOGGER.trace("Created keywords: '{}'", keywords);

        Set<OwsMetadata> metadata = Collections.emptySet();

        Set<TypedProcessInputDescription<?>> inputs = Collections.emptySet();
        Set<TypedProcessOutputDescription<?>> outputs = Collections.emptySet();
        String version = jt.getVersion();
        boolean storeSupported = true;
        boolean statusSupported = true;

        return new ScaleAlgorithm(scaleService,
                jt.getId(),
                id,
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

}
