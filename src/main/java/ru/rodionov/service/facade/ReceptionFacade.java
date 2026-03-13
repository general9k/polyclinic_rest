package ru.rodionov.service.facade;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.dto.ReceptionDTO;
import ru.rodionov.dto.request.CreateReceptionRequest;
import ru.rodionov.dto.request.UpdateReceptionRequest;
import ru.rodionov.mapper.ReceptionMapper;
import ru.rodionov.model.Diagnose;
import ru.rodionov.model.Medicine;
import ru.rodionov.model.Reception;
import ru.rodionov.model.Symptom;
import ru.rodionov.model.User;
import ru.rodionov.service.DiagnoseService;
import ru.rodionov.service.MedicineService;
import ru.rodionov.service.ReceptionService;
import ru.rodionov.service.SymptomService;
import ru.rodionov.service.UserService;
import ru.rodionov.util.JsonNullableHelper;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceptionFacade {

    private final ReceptionService receptionService;
    private final SymptomService symptomService;
    private final DiagnoseService diagnoseService;
    private final MedicineService medicineService;
    private final UserService userService;

    private final ReceptionMapper receptionMapper;

    @Transactional
    public void delete(UUID id) {
        getById(id);
        receptionService.delete(id);
    }

    public ReceptionDTO getById(UUID id) {
        return receptionMapper.toDto(receptionService.getReception(id));
    }

    @Transactional
    public ReceptionDTO createReception(UUID doctorId, UUID userId, CreateReceptionRequest request) {
        User doctor = userService.getById(doctorId);
        User user = userService.getById(userId);

        Set<Symptom> symptoms = new HashSet<>(symptomService.getSymptoms(request.getSymptoms().stream().toList()));
        Set<Diagnose> diagnoses = new HashSet<>(diagnoseService.getDiagnoses(request.getDiagnoses().stream().toList()));
        Set<Medicine> medicines = new HashSet<>(medicineService.getMedicines(request.getMedicines().stream().toList()));

        Reception reception = Reception.builder()
                .dateOfAppointment(request.getDateOfAppointment())
                .wasCarriedOut(false)
                .prescription(request.getPrescription())
                .worker(doctor)
                .patient(user)
                .symptoms(symptoms)
                .diagnoses(diagnoses)
                .medicines(medicines)
                .build();

        return receptionMapper.toDto(receptionService.save(reception));
    }

    public List<ReceptionDTO> getReceptions() {
        List<Reception> receptions = receptionService.findAll();

        receptions = receptions.stream()
                .sorted(Comparator.comparing(Reception::getDateOfAppointment))
                .toList().reversed();
        return receptionMapper.toDto(receptions);
    }

    @Transactional
    public ReceptionDTO updateReception(UUID id, UpdateReceptionRequest request) {
        Reception reception = receptionService.getReception(id);

        if (JsonNullableHelper.hasValueNotNull(request.getDiagnoses())) {
            List<UUID> uuids = request.getDiagnoses().get().stream().toList();
            reception.setDiagnoses(new HashSet<>(diagnoseService.getDiagnoses(uuids)));
        }
        if (JsonNullableHelper.hasValueNotNull(request.getSymptoms())) {
            List<UUID> uuids = request.getSymptoms().get().stream().toList();
            reception.setSymptoms(new HashSet<>(symptomService.getSymptoms(uuids)));
        }
        if (JsonNullableHelper.hasValueNotNull(request.getMedicines())) {
            List<UUID> uuids = request.getMedicines().get().stream().toList();
            reception.setMedicines(new HashSet<>(medicineService.getMedicines(uuids)));
        }
        if (JsonNullableHelper.hasValueNotNull(request.getWasCarriedOut()) &&
            request.getWasCarriedOut().get() == Boolean.TRUE) {
            reception.setWasCarriedOut(Boolean.TRUE);
            reception.setDateInspection(OffsetDateTime.now());
        }
        if (JsonNullableHelper.hasValueNotNull(request.getWasCarriedOut()) &&
            request.getWasCarriedOut().get() == Boolean.FALSE) {
            reception.setWasCarriedOut(Boolean.FALSE);
            reception.setDateInspection(null);
        }
        JsonNullableHelper.updateField(request.getDateOfAppointment(), reception::setDateOfAppointment);
        JsonNullableHelper.updateField(request.getPrescription(), reception::setPrescription);
        return receptionMapper.toDto(receptionService.save(reception));
    }
}
