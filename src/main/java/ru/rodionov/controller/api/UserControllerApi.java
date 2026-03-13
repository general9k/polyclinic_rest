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
import ru.rodionov.controller.api.v1.GeneralApi;
import ru.rodionov.dto.UserDTO;
import ru.rodionov.dto.request.UpdateProfileRequest;
import ru.rodionov.util.exception.ServerError;

import java.util.List;
import java.util.UUID;

@Tag(name = "Пользователи", description = "Операции управления профилями пользователей")
@SecurityRequirement(name = "bearerAuth")
public interface UserControllerApi extends GeneralApi {

    @Operation(
            summary = "Получить профиль пользователя",
            description = "Возвращает полную информацию о профиле пользователя по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Профиль найден",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    ResponseEntity<UserDTO> getProfile(
            @Parameter(description = "ID пользователя", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Частично обновить профиль (PATCH)",
            description = "Обновляет только переданные поля профиля. Используется JsonNullable для различения: поле не передано vs поле = null"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Профиль успешно обновлён",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "422", description = "Email или паспортные данные уже заняты",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/profile/{id}", method = RequestMethod.PATCH)
    ResponseEntity<UserDTO> patchProfile(
            @Parameter(description = "Данные для обновления", required = true)
            @Valid @RequestBody UpdateProfileRequest request,

            @Parameter(description = "ID пользователя", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Получить список всех пользователей (НЕ ВРАЧЕЙ)",
            description = "Возвращает список всех пользователей системы."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Требуется аутентификация",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав (требуется ADMIN)",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    ResponseEntity<List<UserDTO>> getUsers();
}