package ru.rodionov.service.facade;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.dto.DiagnoseDTO;
import ru.rodionov.mapper.DiagnoseMapper;
import ru.rodionov.service.DiagnoseService;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiagnoseFacade {

    private final DiagnoseService diagnoseService;
    private final DiagnoseMapper diagnoseMapper;

    public List<DiagnoseDTO> getDiagnoses() {
        return diagnoseMapper.toDto(diagnoseService.getDiagnoses());
    }

    @Transactional
    public DiagnoseDTO saveDiagnose(String name) {
        return diagnoseMapper.toDto(diagnoseService.save(name));
    }

    @Transactional
    public void deleteDiagnose(UUID id) {
        diagnoseService.delete(id);
    }
}
