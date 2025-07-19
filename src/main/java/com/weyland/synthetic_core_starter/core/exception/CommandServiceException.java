package com.weyland.synthetic_core_starter.core.exception;

public class CommandServiceException extends RuntimeException {
    public CommandServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
