package ru.rodionov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Schema(description = "Роль пользователя в системе")
public class RoleDTO {

    @Schema(description = "Название роли", example = "ROLE_DOCTOR",
            allowableValues = {"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_PATIENT"})
    private String name;

    @Schema(description = "Дата создания роли", example = "2026-01-15T10:00:00Z",
            type = "string", format = "date-time", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime createdAt;
}