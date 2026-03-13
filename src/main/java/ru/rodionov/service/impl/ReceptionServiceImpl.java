package ru.rodionov.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rodionov.model.Reception;
import ru.rodionov.repository.ReceptionRepository;
import ru.rodionov.service.ReceptionService;
import ru.rodionov.util.exception.ServerLogicException;
import ru.rodionov.util.exception.ServerLogicExceptionType;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceptionServiceImpl implements ReceptionService {

    private final ReceptionRepository repository;

    @Override
    public Reception save(Reception reception) {
        return repository.save(reception);
    }

    @Override
    public List<Reception> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Reception getReception(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServerLogicException("Прием с id %s не найден".formatted(id), ServerLogicExceptionType.NOT_FOUND));
    }
}
