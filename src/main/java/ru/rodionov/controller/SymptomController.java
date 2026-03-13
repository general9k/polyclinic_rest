package ru.rodionov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.rodionov.controller.api.SymptomControllerApi;
import ru.rodionov.dto.SymptomDTO;
import ru.rodionov.service.facade.SymptomFacade;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SymptomController implements SymptomControllerApi {

    private final SymptomFacade symptomFacade;

    @Override
    public ResponseEntity<List<SymptomDTO>> getSymptoms() {
        return ResponseEntity.ok(symptomFacade.getSymptoms());
    }

    @Override
    public ResponseEntity<SymptomDTO> saveSymptom(String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(symptomFacade.save(name));
    }

    @Override
    public ResponseEntity<Void> deleteSymptom(UUID id) {
        symptomFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
