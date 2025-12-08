package com.fernando.vote.functions.repository;

import com.fernando.vote.functions.models.containers.Pool;

public interface PoolRepository {
    Pool save(Pool pool);
    Pool getPoolById(String id);
}