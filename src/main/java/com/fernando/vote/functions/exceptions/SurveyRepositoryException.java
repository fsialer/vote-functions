package com.fernando.vote.functions.exceptions;

public class SurveyRepositoryException extends RuntimeException {
    public SurveyRepositoryException(String message) {
        super(message);
    }
    
    public SurveyRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}