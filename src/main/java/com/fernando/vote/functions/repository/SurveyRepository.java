package com.fernando.vote.functions.repository;

import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.models.containers.Vote;

import java.util.List;

public interface SurveyRepository {
    Survey save(Survey survey, List<Option> options);
    Long saveVote(String key);
    Boolean existsKey(String key);
    void appendKey(String key,String value);
}