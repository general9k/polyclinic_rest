package ru.rodionov.service.facade;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.dto.SymptomDTO;
import ru.rodionov.mapper.SymptomMapper;
import ru.rodionov.model.Symptom;
import ru.rodionov.service.SymptomService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SymptomFacade {

    private final SymptomService symptomService;
    private final SymptomMapper symptomMapper;

    public List<SymptomDTO> getSymptoms() {
        return symptomService.getSymptoms().stream().map(symptomMapper::toDto).toList();
    }

    @Transactional
    public SymptomDTO save(String name) {
        return symptomMapper.toDto(symptomService.save(name));
    }

    @Transactional
    public void delete(UUID id) {
        symptomService.getSymptom(id);
        symptomService.delete(id);
    }
}
