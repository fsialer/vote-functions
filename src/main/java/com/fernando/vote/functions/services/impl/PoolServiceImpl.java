package com.fernando.vote.functions.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.vote.functions.models.containers.Pool;
import com.fernando.vote.functions.repository.PoolRepository;
import com.fernando.vote.functions.repository.CacheRepository;
import com.fernando.vote.functions.repository.impl.PoolRepositoryImpl;
import com.fernando.vote.functions.repository.impl.CacheRedisRepositoryImpl;
import com.fernando.vote.functions.services.PoolService;

import java.io.IOException;
import java.util.UUID;

public class PoolServiceImpl implements PoolService {

    private final PoolRepository poolRepository =new PoolRepositoryImpl();
    private final CacheRepository cacheRepository =new CacheRedisRepositoryImpl();

    @Override
    public Pool createPool(Pool pool) {
        String uuid= UUID.randomUUID().toString();
        pool.setId(uuid);
        pool.setPoolId(uuid);
        return poolRepository.save(pool);
    }

    @Override
    public Pool getPool(String id) throws IOException {
        Pool suv=null;
        String keyPool="pool:"+id;
        ObjectMapper obj=new ObjectMapper();
        if(Boolean.TRUE.equals(cacheRepository.existsKey(keyPool))){
            String strPool= cacheRepository.getSet(keyPool);
            suv=obj.readValue( strPool, Pool.class);
        }else{
            suv= poolRepository.getPoolById(id);
            cacheRepository.createSet(keyPool,obj.convertValue(suv,String.class),true);
        }
        return suv;
    }
}
