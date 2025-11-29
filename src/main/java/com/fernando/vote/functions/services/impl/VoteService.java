package com.fernando.vote.functions.services.impl;

import com.fernando.vote.functions.exceptions.VoteSendException;
import com.fernando.vote.functions.models.containers.Vote;
import com.fernando.vote.functions.models.requests.VoteRequest;
import com.fernando.vote.functions.repository.SurveyRepository;
import com.fernando.vote.functions.services.SaveVoteUseCase;

import java.util.Map;
import java.util.UUID;

public class VoteService implements SaveVoteUseCase {
    private final SurveyRepository surveyRepository;

    public VoteService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Long createVote(VoteRequest voteRequest, Map<String, String> headers) {
        String uuid=UUID.randomUUID().toString();
        Vote vote= Vote.builder()
                .id(uuid)
                .poolId(voteRequest.getSurveyId())
                .optionId(voteRequest.getOptionId())
                .ip(headers.get("x-forwarded-for"))
                .device(voteRequest.getDevice())
                .type("VOTE")
                .ttl(60)
                .build();
        String keyVote="vote:"+vote.getPoolId()+":"+vote.getOptionId();
        String keyHistory="unk:"+vote.getPoolId()+":"+vote.getOptionId()+":"+vote.getIp();
        if(Boolean.TRUE.equals(surveyRepository.existsKey(keyHistory))) throw new VoteSendException("Hey you make voted.");
        surveyRepository.appendKey(keyHistory,vote.getIp());
        return surveyRepository.saveVote(keyVote);
    }

}
