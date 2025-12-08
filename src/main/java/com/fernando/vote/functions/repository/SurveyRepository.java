package com.fernando.vote.functions.repository;

import com.fernando.vote.functions.models.containers.Pool;

public interface SurveyRepository {
    Pool save(Pool pool);
    Pool getSurvey(String id);
}