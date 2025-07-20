package com.weyland.starter.core.exception;

public class CommandServiceException extends RuntimeException {
    public CommandServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
