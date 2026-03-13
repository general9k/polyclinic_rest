package ru.rodionov.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Запрос на создание приёма")
public class CreateReceptionRequest {

    @NotNull
    @Schema(description = "Дата и время приёма", example = "2026-03-15T10:00:00Z",
            requiredMode = Schema.RequiredMode.REQUIRED, type = "string", format = "date-time")
    private OffsetDateTime dateOfAppointment;

    @NotNull
    @Length(min = 3, max = 200)
    @Schema(description = "Назначения врача (рецепт)", example = "Принимать по 1 таблетке 3 раза в день",
            minLength = 3, maxLength = 200, requiredMode = Schema.RequiredMode.REQUIRED)
    private String prescription;

    @NotNull
    @Size(min = 1, max = 6)
    @Schema(description = "ID симптомов (от 1 до 6)",
            example = "[\"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\"]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<UUID> symptoms;

    @NotNull
    @Size(min = 1, max = 6)
    @Schema(description = "ID диагнозов (от 1 до 6)",
            example = "[\"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\"]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<UUID> diagnoses;

    @NotNull
    @Size(min = 1, max = 6)
    @Schema(description = "ID лекарств (от 1 до 6)",
            example = "[\"a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11\"]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<UUID> medicines;
}