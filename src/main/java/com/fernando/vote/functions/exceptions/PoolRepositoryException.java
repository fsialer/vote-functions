package com.fernando.vote.functions.exceptions;

public class PoolRepositoryException extends RuntimeException {
    public PoolRepositoryException(String message) {
        super(message);
    }
    
    public PoolRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}