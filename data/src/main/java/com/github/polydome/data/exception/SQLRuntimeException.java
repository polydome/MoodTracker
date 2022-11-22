package com.github.polydome.data.exception;

public class SQLRuntimeException extends RuntimeException {
    public SQLRuntimeException(String errorMessage, Exception exception) {
        super(errorMessage + exception.getMessage());
    } 
}
