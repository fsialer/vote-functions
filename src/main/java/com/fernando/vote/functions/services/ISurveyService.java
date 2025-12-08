package com.fernando.vote.functions.services;

import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.models.requests.SurveyRequest;

import java.io.IOException;
import java.util.Map;

public interface ISurveyService {
    Survey createSurvey(Survey survey);
    Survey getPool(String id) throws IOException;
}
