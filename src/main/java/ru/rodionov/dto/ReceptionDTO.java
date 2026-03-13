package ru.rodionov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Запись о приёме пациента")
public class ReceptionDTO {

    @Schema(description = "Уникальный идентификатор приёма",
            example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Дата и время записи на приём", example = "2026-03-15T10:00:00Z",
            type = "string", format = "date-time")
    private OffsetDateTime dateOfAppointment;

    @Schema(description = "Дата и время осмотра", example = "2026-03-15T10:30:00Z",
            type = "string", format = "date-time", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime dateInspection;

    @Schema(description = "Флаг: приём проведён", example = "true")
    private Boolean wasCarriedOut;

    @Schema(description = "Назначения врача (рецепт)", example = "Принимать по 1 таблетке 3 раза в день",
            minLength = 3, maxLength = 200)
    private String prescription;

    @Schema(description = "Данные врача", implementation = UserDTO.class, accessMode = Schema.AccessMode.READ_ONLY)
    private UserDTO worker;

    @Schema(description = "Данные пациента", implementation = UserDTO.class, accessMode = Schema.AccessMode.READ_ONLY)
    private UserDTO patient;

    @Schema(description = "Список симптомов", implementation = SymptomDTO.class, accessMode = Schema.AccessMode.READ_ONLY)
    private Set<SymptomDTO> symptoms;

    @Schema(description = "Список диагнозов", implementation = DiagnoseDTO.class, accessMode = Schema.AccessMode.READ_ONLY)
    private Set<DiagnoseDTO> diagnoses;

    @Schema(description = "Список назначенных лекарств", implementation = MedicineDTO.class, accessMode = Schema.AccessMode.READ_ONLY)
    private Set<MedicineDTO> medicines;
}
