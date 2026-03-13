package ru.rodionov.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.model.Diagnose;
import ru.rodionov.repository.DiagnoseRepository;
import ru.rodionov.service.DiagnoseService;
import ru.rodionov.util.exception.ServerLogicException;
import ru.rodionov.util.exception.ServerLogicExceptionType;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class DiagnoseServiceImpl implements DiagnoseService {

    private final DiagnoseRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Diagnose> getDiagnoses() {
        return repository.findAll();
    }

    @Override
    public List<Diagnose> getDiagnoses(List<UUID> ids) {
        return ids.stream()
                .map(this::getDiagnose)
                .toList();
    }

    @Override
    @Transactional
    public Diagnose save(String name) {
        return repository.save(Diagnose.builder()
                .name(name)
                .build());
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        getDiagnose(id);
        repository.deleteById(id);
    }

    private Diagnose getDiagnose(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServerLogicException("Диагноз с id %s не найден".formatted(id), ServerLogicExceptionType.NOT_FOUND));
    }
}
