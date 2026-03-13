package ru.rodionov.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.rodionov.controller.api.ReceptionControllerApi;
import ru.rodionov.dto.ReceptionDTO;
import ru.rodionov.dto.request.CreateReceptionRequest;
import ru.rodionov.dto.request.UpdateReceptionRequest;
import ru.rodionov.service.facade.ReceptionFacade;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReceptionController implements ReceptionControllerApi {

    private final ReceptionFacade receptionFacade;

    @Override
    public ResponseEntity<ReceptionDTO> createReception(UUID doctorId,
                                                        UUID userId,
                                                        CreateReceptionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(receptionFacade.createReception(doctorId, userId, request));
    }

    @Override
    public ResponseEntity<List<ReceptionDTO>> getReceptions() {
        return ResponseEntity.ok(receptionFacade.getReceptions());
    }

    @Override
    public ResponseEntity<Void> deleteReceptions(UUID id) {
        receptionFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<ReceptionDTO> updateReception(UUID id, UpdateReceptionRequest request) {
        return ResponseEntity.ok(receptionFacade.updateReception(id, request));
    }
}
