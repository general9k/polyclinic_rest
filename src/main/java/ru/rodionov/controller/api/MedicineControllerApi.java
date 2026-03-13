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
import ru.rodionov.dto.MedicineDTO;
import ru.rodionov.util.exception.ServerError;

import java.util.List;
import java.util.UUID;

@Tag(name = "Лекарства", description = "Операции управления справочником лекарств")
@SecurityRequirement(name = "bearerAuth")
public interface MedicineControllerApi extends GeneralApi {

    @Operation(
            summary = "Получить список лекарств",
            description = "Возвращает список лекарств с возможностью фильтрации по названию"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка",
                    content = @Content(schema = @Schema(implementation = MedicineDTO.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/medicines", method = RequestMethod.GET)
    ResponseEntity<List<MedicineDTO>> getMedicines(
            @Parameter(description = "Фильтр по названию лекарства", example = "Аспирин", required = false)
            @RequestParam(value = "name", required = false) @Valid String name
    );

    @Operation(
            summary = "Создать новое лекарство",
            description = "Добавляет новое лекарство в справочник"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Лекарство успешно создано",
                    content = @Content(schema = @Schema(implementation = MedicineDTO.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Лекарство с таким названием уже существует",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/medicines", method = RequestMethod.POST)
    ResponseEntity<MedicineDTO> createMedicine(
            @Parameter(description = "Данные лекарства", required = true)
            @Valid @RequestBody MedicineDTO request
    );

    @Operation(
            summary = "Обновить лекарство (PUT)",
            description = "Полностью обновляет данные лекарства. Все поля должны быть заполнены."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Лекарство успешно обновлено",
                    content = @Content(schema = @Schema(implementation = MedicineDTO.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Лекарство не найдено",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Название лекарства уже занято",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/medicines/{id}", method = RequestMethod.PUT)
    ResponseEntity<MedicineDTO> updateMedicine(
            @Parameter(description = "Данные лекарства", required = true)
            @Valid @RequestBody MedicineDTO request,

            @Parameter(description = "ID лекарства", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Удалить лекарство",
            description = "Безвозвратно удаляет лекарство из справочника"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Лекарство успешно удалено"),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Лекарство не найдено",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Невозможно удалить: есть связанные приёмы",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/medicines/{id}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMedicine(
            @Parameter(description = "ID лекарства", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @PathVariable UUID id
    );
}
