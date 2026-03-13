package ru.rodionov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.rodionov.dto.DiagnoseDTO;
import ru.rodionov.model.Diagnose;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiagnoseMapper extends EntityMapper<Diagnose, DiagnoseDTO> {
}
