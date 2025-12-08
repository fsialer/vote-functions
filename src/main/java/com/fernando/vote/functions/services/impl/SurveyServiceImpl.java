package com.fernando.vote.functions.services.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.repository.SurveyRepository;
import com.fernando.vote.functions.repository.VoteRepository;
import com.fernando.vote.functions.repository.impl.SurveyRepositoryImpl;
import com.fernando.vote.functions.repository.impl.VoteRepositoryImpl;
import com.fernando.vote.functions.services.ISurveyService;

import java.io.DataInput;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class SurveyServiceImpl implements ISurveyService {

    private final SurveyRepository surveyRepository=new SurveyRepositoryImpl();
    private final VoteRepository voteRepository=new VoteRepositoryImpl();

    @Override
    public Survey createSurvey(Survey survey) {
        String uuid= UUID.randomUUID().toString();
        survey.setId(uuid);
        survey.setPoolId(uuid);
        return surveyRepository.save(survey);
    }

    @Override
    public Survey getPool(String id) throws IOException {
        Survey suv=null;
        String keyPool="pool:"+id;
        ObjectMapper obj=new ObjectMapper();
        if(Boolean.TRUE.equals(voteRepository.existsKey(keyPool))){
            String strPool=voteRepository.getSet(keyPool);
            suv=obj.readValue( strPool,Survey.class);
        }else{
            suv=surveyRepository.getSurvey(id);
            voteRepository.saveSet(keyPool,obj.convertValue(suv,String.class));
        }
        return suv;
    }
}
