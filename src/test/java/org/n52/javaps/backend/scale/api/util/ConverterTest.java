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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.n52.janmayen.http.MediaTypes;
import org.n52.javaps.backend.scale.ScaleAlgorithm;
import org.n52.javaps.backend.scale.ScaleServiceController;
import org.n52.javaps.backend.scale.api.Definition;
import org.n52.javaps.backend.scale.api.InputDatumFile;
import org.n52.javaps.backend.scale.api.InputDatumProperty;
import org.n52.javaps.backend.scale.api.RecipeType;
import org.n52.javaps.description.TypedProcessDescription;

import com.google.common.collect.Lists;

public class ConverterTest {

    private RecipeType recipe;

    @Mock
    ScaleServiceController scaleService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void initRecipeType() {
        String now = ZonedDateTime.now().toString();
        recipe = new RecipeType()
                .withId(23)
                .withName("test-recipe-type-name")
                .withVersion("1.0.0")
                .withTitle("test-recipe-type-title")
                .withDescription("test-recipe-type-descrption")
                .withIsSystem(false)
                .withIsActive(true)
                .withDefinition(new Definition()
                        .withInputData(
                                Lists.newArrayList(
                                        new InputDatumFile()
                                            .withMediaTypes(Collections.singletonList(MediaTypes.APPLICATION_OCTET_STREAM.toString()))
                                            .withRequired(true)
                                            .withName("input_file")
                                        ,
                                        new InputDatumProperty()
                                            .withName("input_property")
                                            .withRequired(true)
                                        )
                                )
                        )
                .withRevision(1)
                .withCreated(now)
                .withLastModified(now);
    }

    @Test
    public void testConvertToAlgorithm() {
        ScaleAlgorithm scaleAlgorithm = new Converter(scaleService).convertToAlgorithm(recipe);
        assertThat(scaleAlgorithm, is(notNullValue()));
        assertThat(scaleAlgorithm.getDescription(), is(notNullValue()));
        TypedProcessDescription description = scaleAlgorithm.getDescription();
        assertThat(description.getId().getValue(), is("scale-algorithm-test-recipe-type-name-1.0.0-r1"));
        assertThat(description.getVersion(), is("1.0.0-r1"));
    }

}
