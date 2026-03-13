package ru.rodionov.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.OffsetDateTime;

@Data
@Schema(description = "Запрос на частичное обновление профиля")
public class UpdateProfileRequest {

    @Length(min = 2, max = 30)
    @Schema(description = "Фамилия", example = "Иванов",
            minLength = 2, maxLength = 30, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> lastName;

    @Length(min = 2, max = 30)
    @Schema(description = "Имя", example = "Иван",
            minLength = 2, maxLength = 30, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> firstName;

    @Length(min = 2, max = 30)
    @Schema(description = "Отчество", example = "Иванович",
            minLength = 2, maxLength = 30, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> patronymic;

    @Email
    @Schema(description = "Email", example = "ivan@example.com",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, type = "string", format = "email")
    private JsonNullable<String> email;

    @Length(min = 2, max = 30)
    @Schema(description = "Серия паспорта", example = "4501",
            minLength = 2, maxLength = 30, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> series;

    @Length(min = 2, max = 30)
    @Schema(description = "Номер паспорта", example = "123456",
            minLength = 2, maxLength = 30, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> number;

    @Length(min = 2, max = 30)
    @Schema(description = "Код подразделения", example = "123-456",
            minLength = 2, maxLength = 30, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> code;

    @Schema(description = "Дата выдачи паспорта", example = "2020-01-15",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, type = "string", format = "date")
    private JsonNullable<OffsetDateTime> issueDate;

    @Length(min = 2, max = 100)
    @Schema(description = "Город", example = "Москва",
            minLength = 2, maxLength = 100, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> city;

    @Length(min = 2, max = 100)
    @Schema(description = "Улица", example = "Ленина",
            minLength = 2, maxLength = 100, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> street;

    @Length(min = 2, max = 100)
    @Schema(description = "Дом", example = "10",
            minLength = 2, maxLength = 100, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> house;

    @Length(min = 2, max = 100)
    @Schema(description = "Квартира", example = "25",
            minLength = 2, maxLength = 100, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> apartment;
}
