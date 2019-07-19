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
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheUpdateTask extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheUpdateTask.class);
    private static final Lock ONE_CACHE_UPDATE_LOCK = new ReentrantLock(true);
    private ScaleRepositoryCache cache;
    private ScaleServiceController scaleService;
    private ScaleConfiguration config;

    public CacheUpdateTask(ScaleRepositoryCache cache, ScaleServiceController scaleService, ScaleConfiguration config) {
        super();
        this.cache = cache;
        this.scaleService = scaleService;
        this.config = config;
    }

    @Override
    public void run() {
        LOGGER.trace("START UPDATE CACHE");
        ONE_CACHE_UPDATE_LOCK.lock();
        try {
            cache.addAlgorithms(scaleService.getAlgorithms());
            LOGGER.info("UPDATED CACHE - next run at {}", nextRun());
        } catch (IOException e) {
            String message = String.format("CACHE UPDATE FAILED: Could not get algorithms from scale web service: %s",
                    e.getMessage());
            LOGGER.error(message);
            LOGGER.debug("Stackstrace:", e);
            LOGGER.info("Next run: {}", nextRun());
        } finally {
            ONE_CACHE_UPDATE_LOCK.unlock();
        }
    }

    private ZonedDateTime nextRun() {
        return ZonedDateTime.now().plus(
                config.getAlgorithmCacheUpdatePeriodInMinutes(),
                ChronoUnit.MINUTES);
    }

}
