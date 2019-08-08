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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.n52.janmayen.http.MediaTypes;
import org.n52.javaps.algorithm.ProcessInputs;
import org.n52.javaps.backend.scale.ScaleAlgorithm;
import org.n52.javaps.backend.scale.ScaleConfiguration;
import org.n52.javaps.backend.scale.ScaleServiceController;
import org.n52.javaps.backend.scale.api.Definition;
import org.n52.javaps.backend.scale.api.InputDatumFile;
import org.n52.javaps.backend.scale.api.InputDatumProperty;
import org.n52.javaps.backend.scale.api.JobData.OutputData;
import org.n52.javaps.backend.scale.api.RecipeData.InputData;
import org.n52.javaps.backend.scale.api.RecipeType;
import org.n52.javaps.description.TypedProcessDescription;
import org.n52.javaps.io.Data;
import org.n52.javaps.io.literal.LiteralData;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.wps.OutputDefinition;

import com.google.common.collect.Lists;

public class ConverterTest {

    private RecipeType recipe;

    @Mock
    ScaleServiceController scaleService;

    @Mock
    ScaleConfiguration scaleConfig;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void initRecipeType() {
        String now = ZonedDateTime.now().toString();
        recipe = (RecipeType) new RecipeType()
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
                .withId(23)
                .withName("test-recipe-type-name")
                .withVersion("1.0.0")
                .withTitle("test-recipe-type-title")
                .withDescription("test-recipe-type-descrption")
                .withIsSystem(false)
                .withIsActive(true)
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

    @Test
    public void testConvertProcessInputsToInputDataList() throws URISyntaxException {

        OwsCode id = new OwsCode("input1");

        String s = "test";

        testLiteralData(id, s);

        URI u = new URI("http://ows.rasdaman.org/rasdaman/ows?service=WMS&version=1.3.0&request=GetMap&layers=BlueMarbleCov&bbox=-90,-180,90,180&width=800&height=600&crs=EPSG:4326&format=image/png&transparent=true&styles=");

        testLiteralData(id, u);

        boolean b = true;

        testLiteralData(id, b);

        int i = -7;

        testLiteralData(id, i);

        double d = 0.25;

        testLiteralData(id, d);
    }

    private void testLiteralData(OwsCode id, Object value) {

      Map<OwsCode, List<Data<?>>> processInputMap = new HashMap<>();

      List<Data<?>> dataList = new ArrayList<Data<?>>();

      LiteralData literalData = new LiteralData(value);

      dataList.add(literalData);
      processInputMap.put(id, dataList);

      List<InputData> inputDataList = new Converter(scaleService).convertProcessInputsToInputData(new ProcessInputs(processInputMap));

      assertTrue(inputDataList != null);
      assertTrue(!inputDataList.isEmpty());

      InputData inputData1 = inputDataList.get(0);

      assertTrue(inputData1 != null);

      assertTrue(inputData1.getName().equals(id.getValue()));
      assertTrue(inputData1.getValue().equals(value.toString()));
    }

    @Test
    public void testConvertProcessOutputsToOutputDataList() {

        when(scaleService.getConfiguration()).thenReturn(scaleConfig);
        when(scaleConfig.getScaleOutputWorkspaceId()).thenReturn(1);

        OwsCode id = new OwsCode("output1");

        Map<OwsCode, Data<?>> processOutputMap = new HashMap<>();

        LiteralData literalData = new LiteralData("");

        processOutputMap.put(id, literalData);

        OutputDefinition outputDefinition = new OutputDefinition(id);

        Collection<OutputDefinition> outputDefinitions = Collections.singletonList(outputDefinition);

        List<OutputData> outputDataList = new Converter(scaleService).convertProcessOutputsToOutputData(outputDefinitions);

        assertTrue(outputDataList != null);
        assertTrue(!outputDataList.isEmpty());

        OutputData outputData1 = outputDataList.get(0);

        assertTrue(outputData1 != null);

        assertTrue(outputData1.getName().equals(id.getValue()));

      }

}
