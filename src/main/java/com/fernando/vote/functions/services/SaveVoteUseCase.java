package com.fernando.vote.functions.services;

import com.fernando.vote.functions.models.requests.VoteRequest;

import java.util.Map;

public interface SaveVoteUseCase {
    Long createVote(VoteRequest voteRequest, Map<String, String> headers);
}
