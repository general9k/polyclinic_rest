package ru.rodionov.service;

import ru.rodionov.model.Symptom;

import java.util.List;
import java.util.UUID;

public interface SymptomService {

    List<Symptom> getSymptoms();

    Symptom getSymptom(UUID id);

    List<Symptom> getSymptoms(List<UUID> ids);

    Symptom save(String name);

    void delete(UUID id);
}
