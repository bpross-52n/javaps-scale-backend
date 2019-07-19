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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.n52.javaps.annotation.ConfigurableClass;
import org.n52.javaps.annotation.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Provides access to the configuration values required for the scale backend.
 *
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 *
 */
@Properties(defaultPropertyFileName="scale_config.default.json", propertyFileName="scale_config.json")
public class ScaleConfiguration implements ConfigurableClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScaleConfiguration.class);

    private static final String SCALE_DCOS_HOST = "scale_dcos_host";
    private static final String SCALE_AUTH_CONTEXTPATH = "scale_auth_contextpath";
    private static final String SCALE_AUTH_TOKEN_LIFETIME = "scale_auth_token_lifetime";
    private static final String SCALE_WEBSERVER_CONTEXTPATH = "scale_webserver_contextpath";
    private static final String SCALE_JWT_AUTH_TOKEN = "scale_jwt_auth_token";
    private static final String SCALE_INFO_COOKIE = "scale_info_cookie";
    private static final String SCALE_ALGORITHM_CACHE_UPDATE_STARTUP_DELAY_SECONDS =
            "scale_algorithm_cache_update_startup_delay_seconds";
    private static final String SCALE_ALGORITHM_CACHE_UPDATE_PERIOD_IN_MINUTES =
            "scale_algorithm_cache_update_period_in_minutes";


    private Optional<URL> scaleAuthEndpoint;
    private Optional<Integer> scaleAuthTokenLifeTime;
    private Optional<URL> scaleWebserverEndpoint;
    private Optional<String> scaleJwtAuthToken;
    private Optional<String> scaleInfoCookie;
    private int scaleAlgorithmCacheUpdateStartUpDelaySeconds;
    private int scaleAlgorithmCacheUpdatePeriodInMinutes;




    public ScaleConfiguration() {
        JsonNode properties = getProperties();
        if (properties == null) {
            String message = String.format("Could not parse properties for class '%s'",
                    this.getClass().getName());
            LOGGER.error(message);
            // FIXME throw new RuntimeException(message);
        } else {
            LOGGER.trace("Reading properties");
            scaleAuthEndpoint = getURL(properties, SCALE_AUTH_CONTEXTPATH);
            scaleAuthTokenLifeTime = Optional.of(properties.get(SCALE_AUTH_TOKEN_LIFETIME).asInt());
            scaleWebserverEndpoint = getURL(properties, SCALE_WEBSERVER_CONTEXTPATH);
            scaleJwtAuthToken = Optional.of(properties.get(SCALE_JWT_AUTH_TOKEN).asText());
            scaleInfoCookie = Optional.of(properties.get(SCALE_INFO_COOKIE).asText());
            scaleAlgorithmCacheUpdateStartUpDelaySeconds =
                    properties.get(SCALE_ALGORITHM_CACHE_UPDATE_STARTUP_DELAY_SECONDS).asInt(0);
            scaleAlgorithmCacheUpdatePeriodInMinutes =
                    properties.get(SCALE_ALGORITHM_CACHE_UPDATE_PERIOD_IN_MINUTES).asInt(5);
        }
        LOGGER.info("NEW {}", this);
    }

    private Optional<URL> getURL(JsonNode properties, String propertyKey) {
        String propertyValue = null;
        String basePath = properties.get(SCALE_DCOS_HOST).asText();
        try {
            propertyValue = properties.get(propertyKey).asText();
            LOGGER.trace("Received value '{}' for key '{}'",
                    propertyValue, propertyKey);
            return Optional.of(new URL(basePath + propertyValue));
        } catch (MalformedURLException e) {
            String message = String.format(
                    "Property '%s' does not contain a valid URI! Received value: '%s'",
                    propertyKey,
                    propertyValue);
            LOGGER.error(message);
            return Optional.empty();
        }
    }

    public Optional<URL> getScaleAuthEndpoint() {
        return scaleAuthEndpoint;
    }

    public Optional<URL> getScaleWebserverEndpoint() {
        return scaleWebserverEndpoint;
    }

    public Optional<Integer> getScaleAuthTokenLifeTime() {
        return scaleAuthTokenLifeTime;
    }

    public Optional<String> getJWTAuthToken() {
        return scaleJwtAuthToken;
    }

    public Optional<String> getInfoCookie() {
        return scaleInfoCookie;
    }

    public int getAlgorithmCacheUpdateStartUpDelaySeconds() {
        return scaleAlgorithmCacheUpdateStartUpDelaySeconds;
    }

    public int getAlgorithmCacheUpdatePeriodInMinutes() {
        return scaleAlgorithmCacheUpdatePeriodInMinutes;
    }

}
