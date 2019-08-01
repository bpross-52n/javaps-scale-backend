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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.n52.javaps.algorithm.IAlgorithm;
import org.n52.javaps.description.TypedProcessDescription;
import org.n52.shetland.ogc.ows.OwsCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This cache provides high performance access to the processing capabilities of the
 * Scale Data Center.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 */
public class ScaleRepositoryCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScaleRepositoryCache.class);

    private Set<OwsCode> algorithmIds;

    private Map<OwsCode, ScaleAlgorithm> algorithms;

    public ScaleRepositoryCache() {
        algorithmIds = Collections.synchronizedSet(new HashSet<>());
        algorithms = Collections.synchronizedMap(new HashMap<>());
        LOGGER.info("NEW {}", this);
    }

    protected Set<OwsCode> getAlgorithmNames() {
        return algorithmIds;
    }

    public Optional<TypedProcessDescription> getProcessDescription(OwsCode id) {
        ScaleAlgorithm algorithm = algorithms.get(id);
        return Optional.ofNullable(algorithm != null ? algorithm.getDescription() : null);
    }

    public boolean contains(OwsCode id) {
        return algorithmIds.contains(id);
    }

    public Optional<IAlgorithm> getAlgorithm(OwsCode id) {
        return Optional.ofNullable(algorithms.get(id));
    }

    public void addAlgorithms(List<ScaleAlgorithm> algorithms) {
        if (algorithms == null || algorithms.isEmpty()) {
            return;
        }
        algorithms.stream().forEach(a -> {
            this.algorithms.put(a.getDescription().getId(), a);
            algorithmIds.add(a.getDescription().getId());
        });
    }

}
