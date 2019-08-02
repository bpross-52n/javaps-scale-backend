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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.n52.javaps.backend.scale.api.QueueRecipe;
import org.n52.javaps.backend.scale.api.RecipeData;
import org.n52.javaps.backend.scale.api.RecipeData.InputData;
import org.n52.javaps.backend.scale.api.Task;
import org.n52.javaps.backend.scale.api.util.ScaleAuthorizationFailedException;
import org.n52.javaps.description.TypedProcessInputDescription;
import org.n52.javaps.description.TypedProcessOutputDescription;
import org.n52.javaps.engine.ProcessExecutionContext;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.ows.OwsKeyword;
import org.n52.shetland.ogc.ows.OwsLanguageString;
import org.n52.shetland.ogc.ows.OwsMetadata;

public class ScaleRecipe extends ScaleAlgorithm {

    public ScaleRecipe(ScaleServiceController scaleService, int scaleId, OwsCode id, OwsLanguageString title,
            OwsLanguageString abstrakt, Set<OwsKeyword> keywords, Set<OwsMetadata> metadata,
            Set<TypedProcessInputDescription<?>> inputs, Set<TypedProcessOutputDescription<?>> outputs,
            String version) {
        super(scaleService, scaleId, id, title, abstrakt, keywords, metadata, inputs, outputs, version);
    }

    @Override
    protected int queueTask(ProcessExecutionContext context) throws IOException, ScaleAuthorizationFailedException {
        return getScaleService().queue(convertToQueueRecipe(context));
    }

    private QueueRecipe convertToQueueRecipe(ProcessExecutionContext context) {
        List<InputData> inputData = Collections.emptyList();
        // TODO convert input data from context to inputdata for Recipe
        // TODO get workspaceId from context or configuration
        Integer workspaceId = 2;
        return new QueueRecipe()
                .withRecipeTypeId(getScaleId())
                .withRecipeData((RecipeData) new RecipeData()
                        .withWorkspaceId(workspaceId)
                        .withInputData(inputData));
    }

    @Override
    protected Task waitForTask(int queuedTaskId) throws IOException, ScaleAuthorizationFailedException {
        return getScaleService().waitForRecipe(queuedTaskId);
    }

}
