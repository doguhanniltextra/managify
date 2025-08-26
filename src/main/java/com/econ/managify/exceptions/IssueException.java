package com.econ.managify.exceptions;

import org.jetbrains.annotations.NotNull;

public class IssueException extends GlobalException {
    public IssueException() {
    }

    public IssueException(String message) {
        super(message);
    }

    public IssueException(String message, Throwable cause) {
        super(message, cause);
    }

    public IssueException(Throwable cause) {
        super(cause);
    }

    public IssueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
