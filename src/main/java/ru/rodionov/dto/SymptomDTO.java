package ru.rodionov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Симптом заболевания")
public class SymptomDTO {

    @Schema(description = "Уникальный идентификатор симптома",
            example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Название симптома", example = "Головная боль",
            minLength = 2, maxLength = 200)
    private String name;
}