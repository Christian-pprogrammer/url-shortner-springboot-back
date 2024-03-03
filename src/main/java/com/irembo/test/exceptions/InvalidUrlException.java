package com.irembo.test.exceptions;

public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException() {
        super();
    }

    public InvalidUrlException(String message) {
        super(message);
    }

    public InvalidUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUrlException(Throwable cause) {
        super(cause);
    }

    protected InvalidUrlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
