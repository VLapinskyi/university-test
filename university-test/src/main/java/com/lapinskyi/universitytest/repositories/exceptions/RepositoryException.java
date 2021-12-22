package com.lapinskyi.universitytest.repositories.exceptions;

public class RepositoryException extends RuntimeException {
    private Exception exception;

    public RepositoryException() {
    }

    public RepositoryException(String repositoryExceptionMessage, Exception exception) {
        super(repositoryExceptionMessage);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
