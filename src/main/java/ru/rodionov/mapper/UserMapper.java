package ru.rodionov.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.rodionov.dto.UserDTO;
import ru.rodionov.dto.request.UpdateProfileRequest;
import ru.rodionov.model.User;
import ru.rodionov.util.JsonNullableHelper;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RoleMapper.class)
public interface UserMapper extends EntityMapper<User, UserDTO> {

    @Override
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDTO userDTO);

    @Override
    UserDTO toDto(User entity);

    UserDTO toUserProfile(User user);

    @Mapping(target = "passport", ignore = true)
    @Mapping(target = "address", ignore = true)
    UserDTO toDoctorDTO(User user);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUpdateEntity(@MappingTarget User entity, UserDTO userDTO);

    default void toUpdateEntity(@MappingTarget User user, UpdateProfileRequest request) {
        JsonNullableHelper.updateField(request.getLastName(), user::setLastName);
        JsonNullableHelper.updateField(request.getFirstName(), user::setFirstName);
        JsonNullableHelper.updateField(request.getPatronymic(), user::setPatronymic);
        JsonNullableHelper.updateField(request.getEmail(), user::setEmail);
        JsonNullableHelper.updateField(request.getSeries(), value -> user.getPassport().setSeries(value));
        JsonNullableHelper.updateField(request.getNumber(), value -> user.getPassport().setNumber(value));
        JsonNullableHelper.updateField(request.getCode(), value -> user.getPassport().setCode(value));
        JsonNullableHelper.updateField(request.getIssueDate(), value -> user.getPassport().setIssueDate(value));
        JsonNullableHelper.updateField(request.getCity(), value -> user.getAddress().setCity(value));
        JsonNullableHelper.updateField(request.getStreet(), value -> user.getAddress().setStreet(value));
        JsonNullableHelper.updateField(request.getHouse(), value -> user.getAddress().setHouse(value));
        JsonNullableHelper.updateField(request.getApartment(), value -> user.getAddress().setApartment(value));
    }

    @Override
    default List<UserDTO> toDto(List<User> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
