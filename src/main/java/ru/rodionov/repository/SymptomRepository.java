package ru.rodionov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.model.Symptom;

import java.util.UUID;

public interface SymptomRepository extends JpaRepository<Symptom, UUID> {
}
