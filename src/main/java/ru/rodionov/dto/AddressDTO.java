package ru.rodionov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Адрес пользователя")
public class AddressDTO {

    @Schema(description = "Уникальный идентификатор адреса",
            example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Город", example = "Москва", minLength = 2, maxLength = 100)
    private String city;

    @Schema(description = "Улица", example = "Ленина", minLength = 2, maxLength = 100)
    private String street;

    @Schema(description = "Номер дома", example = "10", minLength = 1, maxLength = 10)
    private String house;

    @Schema(description = "Номер квартиры", example = "25", minLength = 1, maxLength = 10)
    private String apartment;
}