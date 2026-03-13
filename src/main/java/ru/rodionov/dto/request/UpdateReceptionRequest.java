package ru.rodionov.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Запрос на частичное обновление приёма")
public class UpdateReceptionRequest {

    @Schema(description = "Дата и время приёма", example = "2026-03-15T10:00:00Z",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, type = "string", format = "date-time")
    private JsonNullable<OffsetDateTime> dateOfAppointment;

    @Length(min = 3, max = 200)
    @Schema(description = "Назначения врача (рецепт)", example = "Принимать по 1 таблетке 3 раза в день",
            minLength = 3, maxLength = 200, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<String> prescription;

    @Size(min = 1, max = 6)
    @Schema(description = "ID симптомов", example = "[\"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\"]",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<Set<UUID>> symptoms;

    @Size(min = 1, max = 6)
    @Schema(description = "ID диагнозов", example = "[\"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\"]",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<Set<UUID>> diagnoses;

    @Size(min = 1, max = 6)
    @Schema(description = "ID лекарств", example = "[\"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\"]",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<Set<UUID>> medicines;

    @Schema(description = "Флаг проведения приёма", example = "true",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private JsonNullable<Boolean> wasCarriedOut;
}