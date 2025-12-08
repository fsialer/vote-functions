package com.fernando.vote.functions.mapper;

import com.fernando.vote.functions.models.containers.PoolId;
import com.fernando.vote.functions.models.containers.Vote;
import com.fernando.vote.functions.models.requests.PoolIdRequest;
import com.fernando.vote.functions.models.requests.VoteRequest;

public class VoteMapper {

    public Vote voteRequestToVote(VoteRequest voteRequest){
        return Vote.builder()
                .poolId(voteRequest.getPoolId())
                .optionId(voteRequest.getOptionId())
                .build();
    }

    public PoolId surveyIdRequestToSurveyId(PoolIdRequest poolIdRequest){
        return PoolId.builder()
                .poolId(poolIdRequest.getPoolId())
                .build();
    }
}
