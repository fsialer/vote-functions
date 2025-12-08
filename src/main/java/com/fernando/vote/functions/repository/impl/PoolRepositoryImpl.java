package com.fernando.vote.functions.repository.impl;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.*;
import com.fernando.vote.functions.exceptions.PoolRepositoryException;
import com.fernando.vote.functions.models.containers.Pool;
import com.fernando.vote.functions.repository.PoolRepository;

public class PoolRepositoryImpl implements PoolRepository {

    private static final String COSMOS_ENDPOINT= System.getenv("COSMOS_ENDPOINT");
    private static final String COSMOS_KEY= System.getenv("COSMOS_KEY");
    private static final String COSMO_DB_NAME= System.getenv("COSMO_DB_NAME");
    private static final String COSMO_CONTAINER_NAME= System.getenv("COSMO_CONTAINER_NAME");

    @Override
    public Pool save(Pool pool) {
        try(CosmosClient client = new CosmosClientBuilder()
                .endpoint(COSMOS_ENDPOINT)
                .key(COSMOS_KEY)
                .gatewayMode()
                .buildClient()){
            CosmosContainer container = client
                    .getDatabase(System.getenv(COSMO_DB_NAME))
                    .getContainer(System.getenv(COSMO_CONTAINER_NAME));

            CosmosItemResponse<Pool> response=container.upsertItem(pool,new PartitionKey(pool.getPoolId()),new CosmosItemRequestOptions());
            
            if (response.getStatusCode()>=400) {
                throw new PoolRepositoryException("Failed to save survey with status: " + response.getStatusCode());
            }
        }
        return pool;
    }

    @Override
    public Pool getPoolById(String id) {
        try(CosmosClient client = new CosmosClientBuilder()
                .endpoint(COSMOS_ENDPOINT)
                .key(COSMOS_KEY)
                .gatewayMode()
                .buildClient()){
            CosmosContainer container = client
                    .getDatabase(COSMO_DB_NAME)
                    .getContainer(COSMO_CONTAINER_NAME);
            CosmosItemResponse<Pool> response=container.readItem(id,new PartitionKey("/poolId"), Pool.class);
            return response.getItem();
        }
    }
}