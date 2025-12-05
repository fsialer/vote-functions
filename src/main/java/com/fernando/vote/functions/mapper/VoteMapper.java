package com.fernando.vote.functions.mapper;

import com.fernando.vote.functions.models.containers.SurveyId;
import com.fernando.vote.functions.models.containers.Vote;
import com.fernando.vote.functions.models.requests.SurveyIdRequest;
import com.fernando.vote.functions.models.requests.VoteRequest;

public class VoteMapper {

    public Vote voteRequestToVote(VoteRequest voteRequest){
        return Vote.builder()
                .surveyId(voteRequest.getSurveyId())
                .optionId(voteRequest.getOptionId())
                .build();
    }

    public SurveyId surveyIdRequestToSurveyId(SurveyIdRequest surveyIdRequest){
        return SurveyId.builder()
                .surveyId(surveyIdRequest.getSurveyId())
                .build();
    }
}
