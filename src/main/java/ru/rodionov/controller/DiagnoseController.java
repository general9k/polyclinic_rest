package ru.rodionov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.rodionov.controller.api.DiagnoseControllerApi;
import ru.rodionov.dto.DiagnoseDTO;
import ru.rodionov.service.facade.DiagnoseFacade;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DiagnoseController implements DiagnoseControllerApi {

    private final DiagnoseFacade diagnoseFacade;

    @Override
    public ResponseEntity<List<DiagnoseDTO>> getDiagnoses() {
        return ResponseEntity.ok(diagnoseFacade.getDiagnoses());
    }

    @Override
    public ResponseEntity<DiagnoseDTO> saveDiagnose(String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diagnoseFacade.saveDiagnose(name));
    }

    @Override
    public ResponseEntity<Void> deleteDiagnose(UUID id) {
        diagnoseFacade.deleteDiagnose(id);
        return ResponseEntity.noContent().build();
    }
}
