package com.fernando.vote.functions.services;

import com.fernando.vote.functions.models.containers.Pool;

import java.io.IOException;

public interface PoolService {
    Pool createPool(Pool pool);
    Pool getPool(String id) throws IOException;
}
