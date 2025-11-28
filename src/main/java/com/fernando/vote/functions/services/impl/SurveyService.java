package com.fernando.vote.functions.services.impl;

import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.models.requests.SurveyEvent;
import com.fernando.vote.functions.repository.SurveyRepository;
import com.fernando.vote.functions.services.SaveQuestionUseCase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SurveyService implements SaveQuestionUseCase {
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }


    @Override
    public Survey createSurvey(SurveyEvent surveyEvent) {
        String uuid=UUID.randomUUID().toString();
        Survey survey = Survey.builder()
                .id("SURV-"+uuid)
                .poolId("SURV-"+uuid)
                .type("SURV")
                .closed(false)
                .totalVotes(0)
                .ttl(60)
                .question(surveyEvent.getQuestion())
                .build();
        List<Option> options=surveyEvent.getOptions().stream().map(optionRequest -> Option.builder()
                .id("OP-"+UUID.randomUUID().toString())
                .poolId("SURV"+uuid)
                .type("OP")
                .ttl(60)
                .description(optionRequest.getDescription())
                .quantityVotes(0)
                .build()).collect(Collectors.toList());
        
        return surveyRepository.save(survey,options);
    }
}