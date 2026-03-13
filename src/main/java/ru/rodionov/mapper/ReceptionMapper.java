package ru.rodionov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.rodionov.dto.ReceptionDTO;
import ru.rodionov.model.Reception;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReceptionMapper extends EntityMapper<Reception, ReceptionDTO> {
}
