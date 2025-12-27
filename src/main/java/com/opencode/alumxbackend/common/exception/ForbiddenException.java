package com.opencode.alumxbackend.common.exception;

/**
 * Exception thrown when a user is authenticated but lacks permission to access a resource
 */
public class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbiddenException() {}

    public ForbiddenException(String message) {
        super(message);
    }
}
