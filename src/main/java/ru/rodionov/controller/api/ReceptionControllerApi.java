package ru.rodionov.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodionov.controller.api.v1.GeneralApi;
import ru.rodionov.dto.ReceptionDTO;
import ru.rodionov.dto.request.CreateReceptionRequest;
import ru.rodionov.dto.request.UpdateReceptionRequest;
import ru.rodionov.util.exception.ServerError;

import java.util.List;
import java.util.UUID;

@Tag(name = "Приёмы", description = "Операции управления приёмами пациентов")
@SecurityRequirement(name = "bearerAuth")
public interface ReceptionControllerApi extends GeneralApi {

    @Operation(
            summary = "Создать новый приём",
            description = "Записывает пациента на приём к врачу с указанием симптомов, диагнозов и лекарств"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Приём успешно создан",
                    content = @Content(schema = @Schema(implementation = ReceptionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Врач или пациент не найдены",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Время приёма занято или неверные данные",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/receptions", method = RequestMethod.POST)
    ResponseEntity<ReceptionDTO> createReception(
            @Parameter(description = "ID врача", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @RequestParam UUID doctorId,

            @Parameter(description = "ID пациента", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @RequestParam UUID userId,

            @Parameter(description = "Данные приёма", required = true)
            @Valid @RequestBody CreateReceptionRequest request
    );

    @Operation(
            summary = "Получить список всех приёмов",
            description = "Возвращает список всех записей на приём"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка",
                    content = @Content(schema = @Schema(implementation = ReceptionDTO.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/receptions", method = RequestMethod.GET)
    ResponseEntity<List<ReceptionDTO>> getReceptions();

    @Operation(
            summary = "Удалить приём",
            description = "Отменяет и удаляет запись о приёме"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Приём успешно удалён"),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Приём не найден",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/receptions/{id}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteReceptions(
            @Parameter(description = "ID приёма", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Частично обновить приём (PATCH)",
            description = "Обновляет только переданные поля приёма. Используется JsonNullable для различения: поле не передано vs поле = null"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Приём успешно обновлён",
                    content = @Content(schema = @Schema(implementation = ReceptionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Приём не найден",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Невозможно обновить: приём уже проведён",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/receptions/{id}", method = RequestMethod.PATCH)
    ResponseEntity<ReceptionDTO> updateReception(
            @Parameter(description = "ID приёма", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Поля для обновления", required = true)
            @Valid @RequestBody UpdateReceptionRequest request
    );
}
