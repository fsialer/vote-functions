package com.fernando.vote.functions.mapper;

import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.models.requests.SurveyRequest;

import java.util.stream.Collectors;

public class SurveyMapper {
    public Survey surverRequestToSurvey(SurveyRequest surveyRequest){
        return Survey.builder()
                .question(surveyRequest.getQuestion())
                .options(surveyRequest.getOptions().stream().map(optionRequest -> Option.builder()
                        .optionId("OP"+optionRequest.getOptionId().toString())
                        .text(optionRequest.getDescription())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
