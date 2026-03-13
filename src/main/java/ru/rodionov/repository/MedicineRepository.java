package ru.rodionov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.model.Medicine;

import java.util.UUID;

public interface MedicineRepository extends JpaRepository<Medicine, UUID> {
}
