package ru.rodionov.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.model.Symptom;
import ru.rodionov.repository.SymptomRepository;
import ru.rodionov.service.SymptomService;
import ru.rodionov.util.exception.ServerLogicException;
import ru.rodionov.util.exception.ServerLogicExceptionType;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SymptomServiceImpl implements SymptomService {

    private final SymptomRepository symptomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Symptom> getSymptoms() {
        return symptomRepository.findAll();
    }

    @Override
    public Symptom getSymptom(UUID id) {
        return symptomRepository.findById(id)
                .orElseThrow(() -> new ServerLogicException("Симптом с id %s не найден".formatted(id),
                        ServerLogicExceptionType.NOT_FOUND));
    }

    @Override
    public List<Symptom> getSymptoms(List<UUID> ids) {
        return ids.stream()
                .map(this::getSymptom)
                .toList();
    }

    @Override
    @Transactional
    public Symptom save(String name) {
        return symptomRepository.save(Symptom.builder()
                .name(name)
                .build());
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        symptomRepository.deleteById(id);
    }
}
