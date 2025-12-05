package com.fernando.vote.functions.repository;

import java.util.Map;

public interface VoteRepository {
    Long saveVote(String key,String attribute);
    Boolean existsKey(String key);
    void appendKey(String key,String value);
    Map<String,String> getVotes(String key);
}
