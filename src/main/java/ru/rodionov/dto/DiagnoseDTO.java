package ru.rodionov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Диагноз пациента")
public class DiagnoseDTO {

    @Schema(description = "Уникальный идентификатор диагноза",
            example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Название диагноза", example = "Грипп", minLength = 2, maxLength = 200)
    private String name;
}