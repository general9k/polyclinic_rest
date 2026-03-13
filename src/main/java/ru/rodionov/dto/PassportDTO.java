package ru.rodionov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Schema(description = "Паспортные данные пользователя")
public class PassportDTO {

    @Schema(description = "Уникальный идентификатор записи",
            example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Серия паспорта", example = "4501", minLength = 4, maxLength = 4)
    private String series;

    @Schema(description = "Номер паспорта", example = "123456", minLength = 6, maxLength = 6)
    private String number;

    @Schema(description = "Код подразделения", example = "123-456", minLength = 7, maxLength = 7)
    private String code;

    @Schema(description = "Дата выдачи паспорта", example = "2020-01-15",
            type = "string", format = "date", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime issueDate;
}