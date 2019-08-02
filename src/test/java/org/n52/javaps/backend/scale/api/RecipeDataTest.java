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
package org.n52.javaps.backend.scale.api;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RecipeDataTest {

    @Test
    public void shouldEncodeLiteralInputDatum() throws JsonProcessingException {
        TaskData recipeData = new RecipeData()
                .withVersion("52.42.23")
                .withInputData(Collections.singletonList(
                        new RecipeData.InputData()
                        .withName("test_name")
                        .withValue("test_value")));
        ObjectMapper mapper = new ObjectMapper();
        String jobDataJson = mapper.writeValueAsString(recipeData);
        Assert.assertThat(jobDataJson, Is.is("{" +
                "\"version\":\"52.42.23\"," +
                "\"input_data\":[{" +
                "\"name\":\"test_name\"," +
                "\"value\":\"test_value\"" +
                "}]" +
                "}"));
    }

    @Test
    public void shouldEncodeFileInputDatum() throws JsonProcessingException {
        TaskData recipeData = new RecipeData()
                .withVersion("52.42.23")
                .withInputData(Collections.singletonList(
                        new RecipeData.InputData()
                        .withName("file_name")
                        .withFileId(123456)));
        ObjectMapper mapper = new ObjectMapper();
        String jobDataJson = mapper.writeValueAsString(recipeData);
        Assert.assertThat(jobDataJson, Is.is("{" +
                "\"version\":\"52.42.23\"," +
                "\"input_data\":[{" +
                "\"name\":\"file_name\"," +
                "\"file_id\":123456" +
                "}]" +
                "}"));
    }

    @Test
    public void shouldEncodeFilesInputDatum() throws JsonProcessingException {
        List<Integer> fileIds = new LinkedList<>();
        fileIds.add(123456);
        fileIds.add(123457);
        TaskData recipeData = new RecipeData()
                .withVersion("52.42.23")
                .withInputData(Collections.singletonList(
                        new RecipeData.InputData()
                        .withName("file_names")
                        .withFileIds(fileIds)));
        ObjectMapper mapper = new ObjectMapper();
        String jobDataJson = mapper.writeValueAsString(recipeData);
        Assert.assertThat(jobDataJson, Is.is("{" +
                "\"version\":\"52.42.23\"," +
                "\"input_data\":[{" +
                "\"name\":\"file_names\"," +
                "\"file_ids\":[" +
                "123456," +
                "123457" +
                "]}]" +
                "}"));
    }

    @Test
    public void shouldEncodeOutputData() throws JsonProcessingException {
        TaskData recipeData = new RecipeData()
                .withWorkspaceId(123456)
                .withVersion("52.42.23");
        ObjectMapper mapper = new ObjectMapper();
        String jobDataJson = mapper.writeValueAsString(recipeData);
        Assert.assertThat(jobDataJson, Is.is("{" +
                "\"version\":\"52.42.23\"," +
                "\"workspace_id\":" +
                "123456" +
                "}"));
    }

}
