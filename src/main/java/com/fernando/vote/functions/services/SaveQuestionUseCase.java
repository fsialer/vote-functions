package com.fernando.vote.functions.services;

import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.models.containers.Vote;
import com.fernando.vote.functions.models.requests.SurveyEvent;
import com.fernando.vote.functions.models.requests.VoteRequest;

import java.util.Map;

public interface SaveQuestionUseCase {
    Survey createSurvey(SurveyEvent surveyEvent);
}
