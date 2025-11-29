package com.fernando.vote.functions.repository.impl;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.*;
import com.fernando.vote.functions.exceptions.SurveyRepositoryException;
import com.fernando.vote.functions.exceptions.VoteSendException;
import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.models.containers.Vote;
import com.fernando.vote.functions.repository.SurveyRepository;
import redis.clients.jedis.JedisPooled;

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

    @Override
    public Long saveVote(String key) {
        try(JedisPooled jedis = new JedisPooled(
                System.getenv("REDIS_HOST"),
                Integer.parseInt(System.getenv("REDIS_PORT")),
                Boolean.parseBoolean(System.getenv("REDIS_SSL"))
        )) {
            return jedis.incr(key);
        }
    }

    @Override
    public Boolean existsKey(String key) {
        try(JedisPooled jedis = new JedisPooled(
                System.getenv("REDIS_HOST"),
                Integer.parseInt(System.getenv("REDIS_PORT")),
                Boolean.parseBoolean(System.getenv("REDIS_SSL"))
        )) {
            return jedis.exists(key);
        }
    }

    @Override
    public void appendKey(String key, String value) {
        try(JedisPooled jedis = new JedisPooled(
                System.getenv("REDIS_HOST"),
                Integer.parseInt(System.getenv("REDIS_PORT")),
                Boolean.parseBoolean(System.getenv("REDIS_SSL"))
        )) {
            jedis.append(key,value);
        }
    }
}