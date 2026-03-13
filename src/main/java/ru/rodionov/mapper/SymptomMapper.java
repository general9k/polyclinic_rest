package ru.rodionov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.rodionov.dto.SymptomDTO;
import ru.rodionov.model.Symptom;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SymptomMapper extends EntityMapper<Symptom, SymptomDTO> {
}
