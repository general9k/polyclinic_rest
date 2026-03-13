package ru.rodionov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.rodionov.controller.api.MedicineControllerApi;
import ru.rodionov.dto.MedicineDTO;
import ru.rodionov.service.facade.MedicineFacade;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MedicineController implements MedicineControllerApi {

    private final MedicineFacade medicineFacade;

    @Override
    public ResponseEntity<List<MedicineDTO>> getMedicines(String name) {
        return ResponseEntity.ok(medicineFacade.getMedicines(name));
    }

    @Override
    public ResponseEntity<MedicineDTO> createMedicine(MedicineDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicineFacade.createMedicine(request));
    }

    @Override
    public ResponseEntity<MedicineDTO> updateMedicine(MedicineDTO request, UUID id) {
        return ResponseEntity.ok(medicineFacade.updateMedicine(id, request));
    }


    @Override
    public ResponseEntity<Void> deleteMedicine(UUID id) {
        medicineFacade.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }
}
