package com.github.polydome.data.exception;

/**
 * Unchecked exception used in MoodRepositorySqlite
 * Gets thrown when something goes wrong in operation
 * Holds original checked exception object
 */
public class SQLRuntimeException extends RuntimeException {
    /**
     * Constructor of an exception
     * @param errorMessage error message
     * @param exception original checked exception
     */
    public SQLRuntimeException(String errorMessage, Exception exception) {
        super(errorMessage + exception.getMessage());
    } 
}
