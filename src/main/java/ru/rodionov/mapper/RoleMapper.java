package ru.rodionov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.rodionov.dto.RoleDTO;
import ru.rodionov.model.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper extends EntityMapper<Role, RoleDTO> {
}
