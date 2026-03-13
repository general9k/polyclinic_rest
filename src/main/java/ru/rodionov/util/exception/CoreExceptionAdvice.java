package ru.rodionov.util.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CoreExceptionAdvice {

    // === Форматы сообщений (хардкод, без i18n) ===
    private static final String FORMAT_ARGUMENTS_NOT_VALID = "Некорректный тип данных в параметрах: %s.";
    private static final String FORMAT_ARGUMENTS_IS_EMPTY = "Параметры: %s ,- не могут быть пустыми.";
    private static final String FORMAT_ARGUMENT_IS_INVALID = "Некорректное значение параметра: %s.";
    private static final String FORMAT_ARGUMENT_TYPE_NOT_VALID = "Некорректный тип данных в параметре: %s.";
    private static final String EXCEPTION_AUTHENTICATION = "Ошибка аутентификации.";

    // === ServerLogicException ===
    @ExceptionHandler(ServerLogicException.class)
    public ResponseEntity<ServerError> handleServerLogicException(final ServerLogicException e) {
        ServerError serverError = toServerError(e.getType(), e);
        return ResponseEntity.status(e.getType().getHttpCode()).body(serverError);
    }

    // === MethodArgumentNotValidException (@Valid в body) ===
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerError> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Validation failed: {}", e.getMessage());

        StringBuilder errorMessage = new StringBuilder();

        // Поля с @NotNull (пустые)
        String emptyFields = e.getFieldErrors().stream()
                .filter(error -> ErrorConstants.NOT_NULL_CODE.equals(error.getCode()))
                .map(FieldError::getField)
                .collect(Collectors.joining(ErrorConstants.SEMICOLON_DELIMITER));

        // Поля с другими ошибками валидации (неверный формат, диапазон и т.д.)
        List<String> invalidTypeFields = e.getFieldErrors().stream()
                .filter(error -> !ErrorConstants.NOT_NULL_CODE.equals(error.getCode()))
                .map(this::getInvalidArgumentMessage)
                .distinct()
                .toList();

        if (!emptyFields.isEmpty()) {
            errorMessage.append(String.format(FORMAT_ARGUMENTS_IS_EMPTY, emptyFields));
        }
        if (!invalidTypeFields.isEmpty()) {
            String joinedFields = String.join(ErrorConstants.SEMICOLON_DELIMITER, invalidTypeFields);
            String format = invalidTypeFields.size() == 1
                    ? FORMAT_ARGUMENT_IS_INVALID
                    : "Некорректные значения параметров: %s.";
            errorMessage.append(String.format(format, joinedFields));
        }

        String finalMessage = !errorMessage.isEmpty()
                ? errorMessage.toString()
                : "Ошибка валидации: " + e.getMessage();

        return new ServerErrorBuilder(ServerLogicExceptionType.VALIDATION_ERROR)
                .message(finalMessage)
                .build();
    }

    /**
     * Формирует сообщение для поля с ошибкой валидации (без MessageSource)
     */
    private String getInvalidArgumentMessage(FieldError error) {
        String field = error.getField();
        String defaultMessage = error.getDefaultMessage(); // "не должно равняться null" и т.д.

        // Если есть дефолтное сообщение от аннотации — используем его
        if (defaultMessage != null && !defaultMessage.isBlank()) {
            return String.format("%s: %s", field, defaultMessage);
        }
        // Фоллбэк: просто имя поля
        return field;
    }

    // === MethodArgumentTypeMismatchException (неверный тип параметра) ===
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ServerError> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        ServerLogicExceptionType type = ServerLogicExceptionType.VALIDATION_ERROR;
        String msg = String.format(FORMAT_ARGUMENTS_NOT_VALID, e.getName());
        ServerLogicException ex = new ServerLogicException(msg, type);
        ServerError serverError = toServerError(type, ex);
        return ResponseEntity.status(type.getHttpCode()).body(serverError);
    }

    // === HttpMessageNotReadableException (невалидный JSON) ===
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServerError> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        String msg = "Некорректный формат запроса.";

        if (cause instanceof JsonMappingException jsonEx) {
            List<String> fields = jsonEx.getPath()
                    .stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .filter(Objects::nonNull)
                    .toList();

            if (!fields.isEmpty()) {
                if (cause instanceof ValueInstantiationException vie && vie.getType().isEnumType()) {
                    // Ошибка в enum-значении
                    msg = String.format("Недопустимое значение в поле: %s", String.join(", ", fields));
                } else {
                    // Ошибка типа данных
                    msg = String.format(FORMAT_ARGUMENT_TYPE_NOT_VALID, String.join(", ", fields));
                }
            }
        }

        log.error("JSON parsing failed: {}", e.getMessage());

        return new ServerErrorBuilder(ServerLogicExceptionType.VALIDATION_ERROR)
                .message(msg)
                .build();
    }

    // === MissingRequestHeaderException (нет заголовка) ===
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ServerError> handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        ServerLogicExceptionType type = ServerLogicExceptionType.AUTHENTICATION_ERROR;
        ServerError serverError = new ServerError(type.getErrorCode(), EXCEPTION_AUTHENTICATION);
        return ResponseEntity.status(type.getHttpCode()).body(serverError);
    }

    // === MissingServletRequestParameterException (нет query-параметра) ===
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ServerError> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        ServerLogicExceptionType type = ServerLogicExceptionType.VALIDATION_ERROR;
        String msg = String.format(FORMAT_ARGUMENTS_IS_EMPTY, e.getParameterName());
        ServerError serverError = new ServerError(type.getErrorCode(), msg);
        return ResponseEntity.status(type.getHttpCode()).body(serverError);
    }

    // === ServletRequestBindingException (общая ошибка биндинга) ===
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ServerError> handleServletRequestBindingException(final ServletRequestBindingException e) {
        ServerLogicExceptionType type = ServerLogicExceptionType.VALIDATION_ERROR;
        ServerError serverError = toServerError(type, e);
        return ResponseEntity.status(type.getHttpCode()).body(serverError);
    }

    // === ConstraintViolationException (валидация на уровне сервиса/репозитория) ===
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ServerError> handleConstraintViolationException(final ConstraintViolationException e) {
        ServerLogicExceptionType type = ServerLogicExceptionType.VALIDATION_ERROR;
        String msg = e.getConstraintViolations().stream()
                .map(violation -> String.format("%s: %s",
                        violation.getPropertyPath(),
                        violation.getMessage()))
                .collect(Collectors.joining("; "));

        ServerError serverError = new ServerError(type.getErrorCode(),
                !msg.isEmpty() ? msg : "Ошибка валидации данных");
        return ResponseEntity.status(type.getHttpCode()).body(serverError);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ServerError> handleNoHandlerFoundException(final NoHandlerFoundException e) {
        log.warn("Endpoint not found: {} {}", e.getHttpMethod(), e.getRequestURL());
        ServerLogicExceptionType type = ServerLogicExceptionType.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(type.getHttpCode())
                .body(toServerError(type, e));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ServerError> handleNoHandlerFoundException(final HttpRequestMethodNotSupportedException e) {
        ServerLogicExceptionType type = ServerLogicExceptionType.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(type.getHttpCode())
                .body(toServerError(type, e));
    }

    // === Вспомогательный метод ===
    private ServerError toServerError(final ServerLogicExceptionType type, final Throwable e) {
        log.warn("Unhandled exception [{}]: {}", type, e.getMessage(), e);
        return new ServerError(type.getErrorCode(), e.getMessage());
    }
}