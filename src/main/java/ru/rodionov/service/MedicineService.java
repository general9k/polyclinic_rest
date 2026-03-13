package ru.rodionov.service;

import ru.rodionov.model.Medicine;

import java.util.List;
import java.util.UUID;

public interface MedicineService {

    List<Medicine> getMedicines(String name);

    Medicine getMedicine(UUID id);

    List<Medicine> getMedicines(List<UUID> ids);

    Medicine save(Medicine medicine);

    void deleteMedicine(UUID id);
}
