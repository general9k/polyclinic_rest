package ru.rodionov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.rodionov.controller.api.DoctorControllerApi;
import ru.rodionov.dto.UserDTO;
import ru.rodionov.service.facade.DoctorFacade;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DoctorController implements DoctorControllerApi {

    private final DoctorFacade doctorFacade;

    @Override
    public ResponseEntity<List<UserDTO>> getDoctors(String lastName) {
        return ResponseEntity.ok(doctorFacade.getDoctors(lastName));
    }

    @Override
    public ResponseEntity<UserDTO> getDoctor(UUID id) {
        return ResponseEntity.ok(doctorFacade.getDoctor(id));
    }
}
