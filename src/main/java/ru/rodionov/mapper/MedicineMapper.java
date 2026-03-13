package ru.rodionov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.rodionov.dto.MedicineDTO;
import ru.rodionov.model.Medicine;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicineMapper extends EntityMapper<Medicine, MedicineDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    Medicine toUpdateEntity(@MappingTarget Medicine entity, MedicineDTO medicineDTO);
}
