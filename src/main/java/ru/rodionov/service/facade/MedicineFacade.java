package ru.rodionov.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.dto.MedicineDTO;
import ru.rodionov.mapper.MedicineMapper;
import ru.rodionov.model.Medicine;
import ru.rodionov.service.MedicineService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineFacade {

    private final MedicineService medicineService;
    private final MedicineMapper medicineMapper;

    public List<MedicineDTO> getMedicines(String name) {
        return medicineService.getMedicines(name).stream()
                .map(medicineMapper::toDto).toList();
    }

    public Medicine getMedicine(UUID id) {
        return medicineService.getMedicine(id);
    }

    @Transactional
    public void deleteMedicine(UUID id) {
        getMedicine(id);
        medicineService.deleteMedicine(id);
    }

    @Transactional
    public MedicineDTO updateMedicine(UUID id, MedicineDTO request) {
        Medicine medicine = getMedicine(id);
        medicineMapper.toUpdateEntity(medicine, request);
        return medicineMapper.toDto(medicineService.save(medicine));
    }

    @Transactional
    public MedicineDTO createMedicine(MedicineDTO request) {
        return medicineMapper.toDto(medicineService.save(medicineMapper.toEntity(request)));
    }
}
