package ru.rodionov.service;

import ru.rodionov.model.Diagnose;

import java.util.List;
import java.util.UUID;

public interface DiagnoseService {

    List<Diagnose> getDiagnoses();

    List<Diagnose> getDiagnoses(List<UUID> ids);

    Diagnose save(String name);

    void delete(UUID id);
}
