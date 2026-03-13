package ru.rodionov.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.model.Medicine;
import ru.rodionov.repository.MedicineRepository;
import ru.rodionov.service.MedicineService;
import ru.rodionov.util.exception.ServerLogicException;
import ru.rodionov.util.exception.ServerLogicExceptionType;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Medicine> getMedicines(String name) {
        List<Medicine> medicines = repository.findAll();

        if (name != null && !name.isEmpty()) {
            medicines = medicines.stream()
                    .filter(medicine -> medicine.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        }

        return medicines;
    }

    @Override
    public Medicine getMedicine(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServerLogicException("Лекарство с id %s не найдено".formatted(id),
                        ServerLogicExceptionType.NOT_FOUND));
    }

    @Override
    public List<Medicine> getMedicines(List<UUID> ids) {
        return ids.stream()
                .map(this::getMedicine)
                .toList();
    }

    @Override
    public Medicine save(Medicine medicine) {
        return repository.save(medicine);
    }

    @Override
    @Transactional
    public void deleteMedicine(UUID id) {
        repository.deleteById(id);
    }
}
