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
                        new RecipeData()
                        .withVersion("1.0")
                        .withInputData(inputData)
                        .withWorkspaceId(15));
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
