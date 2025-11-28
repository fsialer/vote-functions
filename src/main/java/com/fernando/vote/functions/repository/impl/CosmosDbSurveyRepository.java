package com.fernando.vote.functions.repository.impl;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosBatch;
import com.azure.cosmos.models.CosmosBatchResponse;
import com.azure.cosmos.models.PartitionKey;
import com.fernando.vote.functions.exceptions.SurveyRepositoryException;
import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.repository.SurveyRepository;

import java.util.List;

public class CosmosDbSurveyRepository implements SurveyRepository {

    @Override
    public Survey save(Survey survey, List<Option> options) {
        try(CosmosClient client = new CosmosClientBuilder()
                .endpoint(System.getenv("COSMOS_ENDPOINT"))
                .key(System.getenv("COSMOS_KEY"))
                .gatewayMode()
                .buildClient()){
            CosmosContainer container = client
                    .getDatabase(System.getenv("COSMO_DB_NAME"))
                    .getContainer(System.getenv("COSMO_CONTAINER_NAME"));

            CosmosBatch batch = CosmosBatch
                    .createCosmosBatch(new PartitionKey(survey.getPoolId()));
            batch.createItemOperation(survey);
            options.forEach(batch::createItemOperation);

            CosmosBatchResponse response = container.executeCosmosBatch(batch);
            
            if (!response.isSuccessStatusCode()) {
                throw new SurveyRepositoryException("Failed to save survey with status: " + response.getStatusCode());
            }
        }
        return survey;
    }
}