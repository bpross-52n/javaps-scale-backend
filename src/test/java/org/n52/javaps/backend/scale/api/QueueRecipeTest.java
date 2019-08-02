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

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.n52.javaps.backend.scale.api.RecipeData.InputData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QueueRecipeTest {

    @Test
    public void shouldEncodeQueueJob() throws JsonProcessingException {
        List<InputData> inputData = new LinkedList<>();
        inputData.add(new RecipeData.InputData()
                .withName("Param 1")
                .withValue("HELLO"));
        inputData.add(new RecipeData.InputData()
                .withName("Param 2")
                .withFileId(9876));
        QueueRecipe queueRecipe = new QueueRecipe()
                .withRecipeTypeId(1234)
                .withRecipeData(
                        (RecipeData) new RecipeData()
                        .withWorkspaceId(15)
                        .withVersion("1.0")
                        .withInputData(inputData));
        ObjectMapper mapper = new ObjectMapper();
        String queueJobJson = mapper.writeValueAsString(queueRecipe);
        Assert.assertThat(queueJobJson, Is.is("{" +
                "\"recipe_type_id\":1234," +
                "\"recipe_data\":{" +
                "\"version\":\"1.0\"," +
                "\"input_data\":[" +
                "{" +
                "\"name\":\"Param 1\"," +
                "\"value\":\"HELLO\"" +
                "}," +
                "{" +
                "\"name\":\"Param 2\"," +
                "\"file_id\":9876" +
                "}" +
                "]," +
                "\"workspace_id\":15" +
                "}" +
                "}"));
    }

}
