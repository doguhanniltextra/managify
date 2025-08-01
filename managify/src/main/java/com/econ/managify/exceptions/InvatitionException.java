package com.econ.managify.exceptions;

public class InvatitionException extends Throwable {
    public InvatitionException() {
    }

    public InvatitionException(String message) {
        super(message);
    }

    public InvatitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvatitionException(Throwable cause) {
        super(cause);
    }

    public InvatitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
