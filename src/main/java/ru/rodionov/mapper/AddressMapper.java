package ru.rodionov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.rodionov.dto.AddressDTO;
import ru.rodionov.model.Address;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper extends EntityMapper<Address, AddressDTO> {
}
