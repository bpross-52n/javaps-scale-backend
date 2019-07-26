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
        RecipeData recipeData = new RecipeData()
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
        RecipeData recipeData = new RecipeData()
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
        RecipeData recipeData = new RecipeData()
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
        RecipeData recipeData = new RecipeData()
                .withVersion("52.42.23")
                .withWorkspaceId(123456);
        ObjectMapper mapper = new ObjectMapper();
        String jobDataJson = mapper.writeValueAsString(recipeData);
        Assert.assertThat(jobDataJson, Is.is("{" +
                "\"version\":\"52.42.23\"," +
                "\"workspace_id\":" +
                "123456" +
                "}"));
    }

}
