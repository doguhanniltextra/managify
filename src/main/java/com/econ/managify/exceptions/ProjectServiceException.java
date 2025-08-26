package com.econ.managify.exceptions;

public class ProjectServiceException extends GlobalException {
    public ProjectServiceException() {
    }

    public ProjectServiceException(String message) {
        super(message);
    }

    public ProjectServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectServiceException(Throwable cause) {
        super(cause);
    }

    public ProjectServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
