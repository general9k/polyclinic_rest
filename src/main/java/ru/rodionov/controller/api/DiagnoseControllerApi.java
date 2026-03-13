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
import ru.rodionov.dto.DiagnoseDTO;
import ru.rodionov.util.exception.ServerError;

import java.util.List;
import java.util.UUID;

@Tag(name = "Диагнозы", description = "Операции управления справочником диагнозов")
@SecurityRequirement(name = "bearerAuth")
public interface DiagnoseControllerApi extends GeneralApi {

    @Operation(
            summary = "Получить список всех диагнозов",
            description = "Возвращает полный список диагнозов из справочника"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка",
                    content = @Content(schema = @Schema(implementation = DiagnoseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/diagnosis", method = RequestMethod.GET)
    ResponseEntity<List<DiagnoseDTO>> getDiagnoses();

    @Operation(
            summary = "Создать новый диагноз",
            description = "Добавляет новый диагноз в справочник"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Диагноз успешно создан",
                    content = @Content(schema = @Schema(implementation = DiagnoseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Диагноз с таким названием уже существует",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/diagnosis", method = RequestMethod.POST)
    ResponseEntity<DiagnoseDTO> saveDiagnose(
            @Parameter(description = "Название диагноза", example = "Грипп", required = true)
            @Valid @RequestParam String name
    );

    @Operation(
            summary = "Удалить диагноз",
            description = "Безвозвратно удаляет диагноз из справочника"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Диагноз успешно удалён"),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Диагноз не найден",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Невозможно удалить: есть связанные приёмы",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/diagnosis/{id}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteDiagnose(
            @Parameter(description = "ID диагноза", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @PathVariable UUID id
    );
}
