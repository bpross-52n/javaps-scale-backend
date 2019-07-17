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
import org.n52.javaps.backend.scale.api.InputDatum;
import org.n52.javaps.backend.scale.api.Recipe;
import org.n52.javaps.backend.scale.api.RecipeType;
import org.n52.javaps.backend.scale.api.RecipeTypeRev;
import org.n52.javaps.backend.scale.api.ReferencedRecipeType;
import org.n52.javaps.description.TypedProcessDescription;

import com.google.common.collect.Lists;

public class ConverterTest {

    private Recipe recipe;

    @Mock
    ScaleServiceController scaleService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void initJobType() {
        String now = ZonedDateTime.now().toString();
        recipe = new Recipe()
                .withId(23)
                .withCreated(now)
                .withLastModified(now)
                .withRecipeType(new RecipeType()
                        .withId(42)
                        .withName("test-recipe-type-name")
                        .withRevision(1)
                        .withVersion("1.0.0")
                        .withTitle("test-recipe-type-title")
                        .withDescription("test-recipe-type-descrption")
                        .withIsSystem(false)
                        .withIsActive(true))
                .withRecipeTypeRev(new RecipeTypeRev()
                        .withId(42)
                        .withRecipeType(new ReferencedRecipeType().withId(42))
                        .withRevision(1)
                        .withCreated(now)
                        .withDefinition(new Definition()
                                .withInputData(Lists.newArrayList(new InputDatum()
                                        .withRequired(true)
                                        .withType("file")
                                        .withName("input_file")
                                        // FIXME change to APPLICATION_OCTET_STREAM when javaPS changed to new arctic-sea version
                                        .withMediaTypes(Collections.singletonList(MediaTypes.APPLICATION_XML.toString()))
                                        ))
                                )
                        );
    }

    @Test
    public void testConvertToAlgorithm() {
        ScaleAlgorithm scaleAlgorithm = new Converter(scaleService).convertToAlgorithm(recipe);
        assertThat(scaleAlgorithm, is(notNullValue()));
        assertThat(scaleAlgorithm.getDescription(), is(notNullValue()));
        TypedProcessDescription description = scaleAlgorithm.getDescription();
        assertThat(description.getId().getValue(), is("scale-algorithm-test-recipe-type-name-v1.0.0-r1"));
        assertThat(description.getVersion(), is("v1.0.0-r1"));
    }

}
