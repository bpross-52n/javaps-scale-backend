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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.n52.javaps.backend.scale.api.Job;
import org.n52.javaps.backend.scale.api.QueueJob;
import org.n52.javaps.backend.scale.api.QueueRecipe;
import org.n52.javaps.backend.scale.api.Recipe;
import org.n52.javaps.backend.scale.api.util.ScaleAuthorizationFailedException;
import org.n52.javaps.test.AbstractTestCase;

public class ScaleServiceControllerTest extends AbstractTestCase {

    @Resource
    private ScaleServiceController scaleService;

    private boolean isInit;

    @Before
    public void initScaleService() {
        if (!isInit) {
            scaleService.init();
            isInit = true;
        }
    }

    @Test
    public void testResourceInjection() {
        assertThat(scaleService, is(notNullValue()));
    }

    @Test
    public void testGetAlgorithms() throws IOException, ScaleAuthorizationFailedException {
        List<ScaleAlgorithm> algorithms = scaleService.getAlgorithms();
        assertThat(algorithms, is(notNullValue()));
    }

    @Test
    public void testQueueRecipe() throws IOException, ScaleAuthorizationFailedException {
        QueueRecipe queueRecipe = new QueueRecipe()
                .withRecipeTypeId(1);
        // recipe type 1 is currently Scale Casino 1.0
        // http://gmudcos.hopto.org/service/scale/#/recipes/types/1
        int queuedRecipeId = scaleService.queue(queueRecipe);
        assertThat(queuedRecipeId, is(not(-1)));
    }

    @Test
    @Ignore("Waiting for recipe of type 1 to finish takes to long")
    public void testWaitForRecipe() throws IOException, ScaleAuthorizationFailedException {
        QueueRecipe queueRecipe = new QueueRecipe()
                .withRecipeTypeId(1);
        // recipe type 1 is currently Scale Casino 1.0
        // http://gmudcos.hopto.org/service/scale/#/recipes/types/1
        int queuedRecipeId = scaleService.queue(queueRecipe);
        Recipe finishedRecipe = scaleService.waitForRecipe(queuedRecipeId);
        assertThat(finishedRecipe, is(notNullValue()));
    }

    @Test
    public void testQueueJob() throws IOException, ScaleAuthorizationFailedException {
        QueueJob queueJob = new QueueJob()
                .withJobTypeId(2);
        // job type 2 is currently scale-hello
        // http://gmudcos.hopto.org/service/scale/#/jobs/types/2
        int queuedJobId = scaleService.queue(queueJob);
        assertThat(queuedJobId, is(not(-1)));
    }

    @Test
    @Ignore("Waiting for job of type 2 to finish takes to long")
    public void testWaitForJob() throws IOException, ScaleAuthorizationFailedException {
        QueueJob queueJob = new QueueJob()
                .withJobTypeId(2);
        // job type 2 is currently scale-hello
        // http://gmudcos.hopto.org/service/scale/#/jobs/types/2
        int queuedJobId = scaleService.queue(queueJob);
        Job finishedJob = scaleService.waitForJob(queuedJobId);
        assertThat(finishedJob, is(notNullValue()));
    }

}
