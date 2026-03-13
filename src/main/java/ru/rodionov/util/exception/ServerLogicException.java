package ru.rodionov.util.exception;

import lombok.Getter;

@Getter
public class ServerLogicException extends CoreException {
    private final ServerLogicExceptionType type;

    public ServerLogicException(final ServerLogicExceptionType type) {
        super(type.getReplacePattern() == null ? type.getMessage() : type.getMessage().replace(type.getReplacePattern(), ""));
        this.type = type;
    }

    public ServerLogicException(final ServerLogicExceptionType type, final Throwable cause) {
        super(type.getReplacePattern() == null ? type.getMessage() : type.getMessage().replace(type.getReplacePattern(), ""), cause);
        this.type = type;
    }

    public ServerLogicException(final ServerLogicExceptionType type, final String object) {
        super(type.getMessage().replace(type.getReplacePattern(), object));
        this.type = type;
    }

    public ServerLogicException(final ServerLogicExceptionType type, final String object, final Throwable cause) {
        super(type.getMessage().replace(type.getReplacePattern(), object), cause);
        this.type = type;
    }

    public ServerLogicException(final String message, final ServerLogicExceptionType type) {
        super(message);
        this.type = type;
    }

    public ServerLogicException(final String message, final ServerLogicExceptionType type, final Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public ServerError toServerError() {
        return new ServerError(this.type.getErrorCode(), this.getMessage());
    }

}
