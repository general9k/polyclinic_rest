package ru.rodionov.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface EntityMapper<E, DTO> {

    E toEntity(DTO dto);

    DTO toDto(E entity);

    List<DTO> toDto(List<E> entities);

    E toUpdateEntity(@MappingTarget E entity, DTO dto);
}
