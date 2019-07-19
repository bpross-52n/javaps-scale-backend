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

import java.util.Optional;
import java.util.Set;
import java.util.Timer;

import javax.inject.Inject;

import org.n52.javaps.algorithm.AlgorithmRepository;
import org.n52.javaps.algorithm.IAlgorithm;
import org.n52.javaps.description.TypedProcessDescription;
import org.n52.shetland.ogc.ows.OwsCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A <code>ScaleRepository</code> provides an {@link AlgorithmRepository} for an
 * <a href="https://ngageoint.github.io/scale/">Scale Data Center</a> instance. Hence,
 * the <a href="http://52north.github.io/javaPS/">javaps</a> can provide access
 * to the processing capabilities of it.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 */
public class ScaleRepository implements AlgorithmRepository  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScaleRepository.class);

    private Timer cacheTimer;

    @Inject
    private ScaleServiceController scaleService;

    @Inject
    private ScaleRepositoryCache cache;

    @Inject
    private ScaleConfiguration config;

    public ScaleRepository() {
        LOGGER.info("NEW {}", this);
    }

    @Override
    public Set<OwsCode> getAlgorithmNames() {
        return cache.getAlgorithmNames();
    }

    @Override
    public Optional<IAlgorithm> getAlgorithm(OwsCode id) {
        return cache.getAlgorithm(id);
    }

    @Override
    public Optional<TypedProcessDescription> getProcessDescription(OwsCode id) {
        return cache.getProcessDescription(id);
    }

    @Override
    public boolean containsAlgorithm(OwsCode id) {
        return cache.contains(id);
    }

    @Override
    public void init() {
        LOGGER.trace("START INIT {}", this);
        // FIXME add lock to limit to one TimeTask at a time
        cacheTimer = new Timer("ScaleAlgorithmRepoUpdateTimer");
        cacheTimer.schedule(new CacheUpdateTask(cache, scaleService, config),
                        config.getAlgorithmCacheUpdateStartUpDelaySeconds() * 1000,
                        config.getAlgorithmCacheUpdatePeriodInMinutes() * 1000 * 60L);
        LOGGER.info("INIT {}", this);
    }

    @Override
    public void destroy() {
        LOGGER.trace("START DESCTROY {}", this);
        cacheTimer.cancel();
        LOGGER.info("DESTROY {}", this);
    }

}
