package com.fernando.vote.functions.services;

import com.fernando.vote.functions.models.containers.PoolId;
import com.fernando.vote.functions.models.containers.Vote;

public interface IVoteService {
    long registerVote(Vote vote);
    void synVotes(PoolId poolId);
}
