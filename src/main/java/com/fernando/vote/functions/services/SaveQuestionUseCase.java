package com.fernando.vote.functions.services;

import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.models.requests.SurveyEvent;

public interface SaveQuestionUseCase {
    Survey createSurvey(SurveyEvent surveyEvent);
}
