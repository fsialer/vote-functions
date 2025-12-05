package com.fernando.vote.functions.repository;

import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Survey;

import java.util.List;

public interface SurveyRepository {
    Survey save(Survey survey);
    Survey getSurvey(String id);
}