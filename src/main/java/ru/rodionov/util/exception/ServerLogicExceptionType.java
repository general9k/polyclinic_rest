package ru.rodionov.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ServerLogicExceptionType {
    VALIDATION_ERROR(ServerError.CodeEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST,
            String.format("Параметры: %s не могут быть пустыми; некорректный тип данных в параметре %s",
                    Constants.FIELD_PATTERN, Constants.FIELD_PATTERN), Constants.FIELD_PATTERN, null, null),
    AUTHENTICATION_ERROR(ServerError.CodeEnum.AUTHENTICATION_ERROR, HttpStatus.UNAUTHORIZED,
            String.format("Ошибка в токене: %s", Constants.OBJECT_PATTERN), Constants.OBJECT_PATTERN, null, null),
    FORBIDDEN_ERROR(ServerError.CodeEnum.ACCESS_DENIED, HttpStatus.FORBIDDEN, "Недостаточно прав", Constants.REASON_PATTERN, null, null),
    METHOD_NOT_ALLOWED(ServerError.CodeEnum.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED,
            String.format("Метод не поддерживается. Доступные методы: %s", Constants.FIELD_PATTERN), Constants.FIELD_PATTERN, null, null),
    NOT_FOUND(ServerError.CodeEnum.ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND,
            String.format("Объект %s не найден", Constants.OBJECT_PATTERN), Constants.OBJECT_PATTERN, null, null),
    ALREADY_EXISTS(ServerError.CodeEnum.ENTITY_ALREADY_EXISTS, HttpStatus.CONFLICT,
            String.format("Объект %s уже существует", Constants.OBJECT_PATTERN), Constants.OBJECT_PATTERN, null, null),
    CONFLICT(ServerError.CodeEnum.ENTITY_ACCESS_CONFLICT, HttpStatus.CONFLICT,
            String.format("Конфликт обращения к объекту: %s", Constants.OBJECT_PATTERN), Constants.OBJECT_PATTERN, null, null),
    UNSUPPORTED_MEDIA_TYPE_ERROR(ServerError.CodeEnum.UNSUPPORTED_MEDIA_TYPE, HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            "Не поддерживаемый тип файла: {mimeType}", "{mimeType}", null, null),
    ENTITY_INVARIABLE(ServerError.CodeEnum.ENTITY_INVARIABLE, HttpStatus.UNPROCESSABLE_ENTITY,
            String.format("Объект %s не может быть изменен", Constants.OBJECT_PATTERN), Constants.OBJECT_PATTERN, null, null),
    NOT_ACCEPTABLE(ServerError.CodeEnum.NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE, "", Constants.OBJECT_PATTERN, null, null),

    DELETE_ERROR(ServerError.CodeEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST,
            String.format("Ошибка удаления: %s", Constants.OBJECT_PATTERN), Constants.OBJECT_PATTERN, null, null),
    REPUTATION_ERROR(ServerError.CodeEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST,
            String.format("Ошибка репутации: %s", Constants.OBJECT_PATTERN), Constants.OBJECT_PATTERN, null, null),
    ANSWERED_ERROR(ServerError.CodeEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST,
            String.format("Ошибка: %s", Constants.OBJECT_PATTERN), Constants.OBJECT_PATTERN, null, null),


    DATABASE_ERROR(ServerError.CodeEnum.DATABASE_REQUEST_EX_EPTION, HttpStatus.INTERNAL_SERVER_ERROR,
            String.format("Ошибка при обращении к базе: %s", Constants.REASON_PATTERN), Constants.REASON_PATTERN, null, null),
    GATEWAY_ERROR(ServerError.CodeEnum.SERVICE_CALL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
            String.format("Ошибка при обращении к внешнему сервису: %s", Constants.REASON_PATTERN), Constants.REASON_PATTERN, null, null),
    SERVICE_ERROR(ServerError.CodeEnum.SERVICE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
            String.format("Внутренняя ошибка сервиса: %s", Constants.REASON_PATTERN), Constants.REASON_PATTERN, null, null),

    COMMON_VALIDATION_ERROR(ServerError.CodeEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST, String.format("%s",
            Constants.FIELD_PATTERN), Constants.FIELD_PATTERN, null, null);

    private final ServerError.CodeEnum errorCode;
    private final HttpStatus httpCode;
    private final String message;
    private final String replacePattern;
    private final String localizationKey;
    private final String localizationNamespace;
}