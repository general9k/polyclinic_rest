package ru.rodionov.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class ServerErrorBuilder {
    private ServerLogicExceptionType type;
    private HttpStatus httpStatus;
    private ServerError.CodeEnum errorCode;
    private String message;
    private Object arguments;

    public ServerErrorBuilder(ServerError.CodeEnum errorCode) {
        this.errorCode = Objects.requireNonNull(errorCode);
    }

    public ServerErrorBuilder(ServerLogicExceptionType type) {
        this.errorCode = Objects.requireNonNull(type.getErrorCode());
        this.type = Objects.requireNonNull(type);
    }

    public ServerErrorBuilder(ServerLogicException ex) {
        this.errorCode = Objects.requireNonNull(ex.getType().getErrorCode());
        this.type = Objects.requireNonNull(ex.getType());
    }

    public ServerErrorBuilder type(ServerLogicExceptionType type) {
        this.type = type;
        return this;
    }

    public ServerErrorBuilder httpCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public ServerErrorBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntity<ServerError> build() {
        ServerError error = new ServerError();
        error.setCode(errorCode);

        if (type != null) {
            this.errorCode = type.getErrorCode();
            if (httpStatus == null)
                this.httpStatus = type.getHttpCode();
            if (message == null)
                this.message = type.getMessage();
        }

        if (errorCode != null)
            error.setCode(errorCode);
        if (message != null)
            error.setMessage(message);

        return ResponseEntity.status(httpStatus).body(error);
    }
}
