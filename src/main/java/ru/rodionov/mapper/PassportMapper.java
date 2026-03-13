package ru.rodionov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.rodionov.dto.PassportDTO;
import ru.rodionov.model.Passport;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassportMapper extends EntityMapper<Passport, PassportDTO> {
}
