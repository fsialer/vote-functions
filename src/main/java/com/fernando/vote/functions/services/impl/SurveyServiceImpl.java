package com.fernando.vote.functions.services.impl;

import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.repository.SurveyRepository;
import com.fernando.vote.functions.repository.impl.SurveyRepositoryImpl;
import com.fernando.vote.functions.services.ISurveyService;

import java.util.UUID;

public class SurveyServiceImpl implements ISurveyService {

    private final SurveyRepository surveyRepository=new SurveyRepositoryImpl();

    @Override
    public Survey createSurvey(Survey survey) {
        String uuid= UUID.randomUUID().toString();
        survey.setId(uuid);
        survey.setPoolId(uuid);
        return surveyRepository.save(survey);
    }
}
