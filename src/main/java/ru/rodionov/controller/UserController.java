package ru.rodionov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.controller.api.UserControllerApi;
import ru.rodionov.dto.UserDTO;
import ru.rodionov.dto.request.UpdateProfileRequest;
import ru.rodionov.service.facade.UserFacade;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserControllerApi {

    private final UserFacade userFacade;

    @Override
    public ResponseEntity<UserDTO> getProfile(UUID id) {
        return ResponseEntity.ok(userFacade.getUserProfile(id));
    }

    @Override
    @Transactional
    public ResponseEntity<UserDTO> patchProfile(UpdateProfileRequest request, UUID id) {
        return ResponseEntity.ok(userFacade.patchProfile(id, request));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userFacade.getUsers());
    }


}
