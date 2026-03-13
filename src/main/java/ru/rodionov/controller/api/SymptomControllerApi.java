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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodionov.controller.api.v1.GeneralApi;
import ru.rodionov.dto.SymptomDTO;
import ru.rodionov.util.exception.ServerError;

import java.util.List;
import java.util.UUID;

@Tag(name = "Симптомы", description = "Операции управления справочником симптомов")
@SecurityRequirement(name = "bearerAuth")
public interface SymptomControllerApi extends GeneralApi {

    @Operation(
            summary = "Получить список всех симптомов",
            description = "Возвращает полный список симптомов из справочника"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка",
                    content = @Content(schema = @Schema(implementation = SymptomDTO.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/symptoms", method = RequestMethod.GET)
    ResponseEntity<List<SymptomDTO>> getSymptoms();

    @Operation(
            summary = "Создать новый симптом",
            description = "Добавляет новый симптом в справочник"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Симптом успешно создан",
                    content = @Content(schema = @Schema(implementation = SymptomDTO.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Симптом с таким названием уже существует",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/symptoms", method = RequestMethod.POST)
    ResponseEntity<SymptomDTO> saveSymptom(
            @Parameter(description = "Название симптома", example = "Головная боль", required = true)
            @Valid @RequestParam String name
    );

    @Operation(
            summary = "Удалить симптом",
            description = "Безвозвратно удаляет симптом из справочника"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Симптом успешно удалён"),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Симптом не найден",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Невозможно удалить: есть связанные приёмы",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/symptoms/{id}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSymptom(
            @Parameter(description = "ID симптома", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @PathVariable UUID id
    );
}