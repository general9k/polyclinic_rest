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
import ru.rodionov.dto.UserDTO;
import ru.rodionov.util.exception.ServerError;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Tag(name = "Врачи", description = "Операции управления данными врачей")
@SecurityRequirement(name = "bearerAuth")
public interface DoctorControllerApi extends GeneralApi {

    @Operation(
            summary = "Получить список врачей",
            description = "Возвращает список врачей с возможностью фильтрации по фамилии"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/doctors", method = RequestMethod.GET)
    ResponseEntity<List<UserDTO>> getDoctors(
            @Parameter(description = "Фильтр по фамилии врача", example = "Иванов", required = false)
            @RequestParam(value = "lastName", required = false) @Valid String lastName
    );

    @Operation(
            summary = "Получить врача по ID",
            description = "Возвращает полную информацию о враче по уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Врач найден",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Врач не найден",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    ResponseEntity<UserDTO> getDoctor(
            @Parameter(description = "ID врача", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @PathVariable UUID id
    ) throws IOException;
}