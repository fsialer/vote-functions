package com.fernando.vote.functions.services;

import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.models.requests.SurveyRequest;

public interface ISurveyService {
    Survey createSurvey(Survey survey);
}
