package ru.rodionov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@Schema(description = "Лекарственный препарат")
public class MedicineDTO {

    @Schema(description = "Уникальный идентификатор лекарства",
            example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotNull
    @Length(min = 3, max = 100)
    @Schema(description = "Название лекарства", example = "Аспирин",
            requiredMode = Schema.RequiredMode.REQUIRED, minLength = 3, maxLength = 100)
    private String name;

    @Length(min = 3, max = 1000)
    @Schema(description = "Способ применения", example = "Принимать внутрь после еды",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, minLength = 3, maxLength = 1000)
    private String method;

    @Length(min = 3, max = 50)
    @Schema(description = "Единица измерения", example = "мг",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, minLength = 3, maxLength = 50)
    private String measuring;

    @Length(min = 3, max = 1000)
    @Schema(description = "Побочные эффекты", example = "Возможна аллергическая реакция",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, minLength = 3, maxLength = 1000)
    private String sideEffects;
}
