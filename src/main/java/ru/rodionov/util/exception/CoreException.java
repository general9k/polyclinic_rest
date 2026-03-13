package ru.rodionov.util.exception;

public class CoreException extends RuntimeException {
    public CoreException(final String description, final Throwable cause) {
        super(description, cause);
    }

    public CoreException(final String description) {
        super(description);
    }
}
