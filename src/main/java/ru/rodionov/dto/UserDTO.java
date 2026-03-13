package ru.rodionov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Данные пользователя (пациент или врач)")
public class UserDTO {

    @Schema(description = "Уникальный идентификатор пользователя",
            example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Роль пользователя", implementation = RoleDTO.class,
            accessMode = Schema.AccessMode.READ_ONLY)
    private RoleDTO role;

    @Schema(description = "Фамилия", example = "Иванов", minLength = 2, maxLength = 30)
    private String lastName;

    @Schema(description = "Имя", example = "Иван", minLength = 2, maxLength = 30)
    private String firstName;

    @Schema(description = "Отчество", example = "Иванович", minLength = 2, maxLength = 30)
    private String patronymic;

    @Schema(description = "Должность (для врачей)", example = "Терапевт",
            minLength = 2, maxLength = 100)
    private String position;

    @Schema(description = "Описание/о себе", example = "Врач высшей категории",
            minLength = 0, maxLength = 1000)
    private String description;

    @Schema(description = "Образование", example = "МГМУ им. Сеченова",
            minLength = 2, maxLength = 200)
    private String education;

    @Schema(description = "Email", example = "ivan@example.com",
            type = "string", format = "email", minLength = 5, maxLength = 100)
    private String email;

    @Schema(description = "URL фотографии", example = "https://example.com/photos/ivan.jpg",
            type = "string", format = "uri")
    private String photoUrl;

    @Schema(description = "Номер телефона", example = "89991234567",
            pattern = "^(8)\\d{10}$", minLength = 11, maxLength = 11)
    private String phoneNumber;

    @Schema(description = "Номер медицинской карты", example = "MED-123456",
            minLength = 5, maxLength = 20)
    private String medicalNumber;

    @Schema(description = "Паспортные данные", implementation = PassportDTO.class)
    private PassportDTO passport;

    @Schema(description = "Адрес проживания", implementation = AddressDTO.class)
    private AddressDTO address;
}