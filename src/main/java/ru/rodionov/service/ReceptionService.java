package ru.rodionov.service;

import ru.rodionov.model.Reception;

import java.util.List;
import java.util.UUID;

public interface ReceptionService {

    Reception save(Reception reception);

    List<Reception> findAll();

    void delete(UUID id);

    Reception getReception(UUID id);
}
