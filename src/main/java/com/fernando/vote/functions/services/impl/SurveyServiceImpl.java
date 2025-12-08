package com.fernando.vote.functions.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.vote.functions.models.containers.Pool;
import com.fernando.vote.functions.repository.SurveyRepository;
import com.fernando.vote.functions.repository.CacheRepository;
import com.fernando.vote.functions.repository.impl.SurveyRepositoryImpl;
import com.fernando.vote.functions.repository.impl.CacheRedisRepositoryImpl;
import com.fernando.vote.functions.services.ISurveyService;

import java.io.IOException;
import java.util.UUID;

public class SurveyServiceImpl implements ISurveyService {

    private final SurveyRepository surveyRepository=new SurveyRepositoryImpl();
    private final CacheRepository cacheRepository =new CacheRedisRepositoryImpl();

    @Override
    public Pool createSurvey(Pool pool) {
        String uuid= UUID.randomUUID().toString();
        pool.setId(uuid);
        pool.setPoolId(uuid);
        return surveyRepository.save(pool);
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
            suv=surveyRepository.getSurvey(id);
            cacheRepository.createSet(keyPool,obj.convertValue(suv,String.class),true);
        }
        return suv;
    }
}
