package ru.rodionov.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.rodionov.controller.api.v1.GeneralApi;
import ru.rodionov.dto.jwt.JwtResponse;
import ru.rodionov.dto.jwt.LoginRequest;
import ru.rodionov.util.exception.ServerError;

@Tag(name = "Аутентификация", description = "Операции входа в систему и получения JWT-токена")
public interface AuthControllerApi extends GeneralApi {

    @Operation(
            summary = "Вход в систему",
            description = "Аутентификация пользователя по логину и паролю. " +
                          "При успешной аутентификации возвращается JWT-токен, который необходимо использовать " +
                          "в заголовке `Authorization: Bearer <token>` для всех защищённых запросов. " +
                          "Время жизни токена настраивается в конфигурации приложения (по умолчанию 24 часа)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная аутентификация",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный формат запроса",
                    content = @Content(schema = @Schema(implementation = ServerError.class))),
            @ApiResponse(responseCode = "401", description = "Неверный логин или пароль",
                    content = @Content(schema = @Schema(implementation = ServerError.class)))
    })
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    ResponseEntity<JwtResponse> login(
            @Parameter(description = "Учётные данные пользователя", required = true)
            @RequestBody LoginRequest request
    );
}