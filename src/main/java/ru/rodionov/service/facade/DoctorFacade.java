package ru.rodionov.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rodionov.dto.UserDTO;
import ru.rodionov.mapper.UserMapper;
import ru.rodionov.model.User;
import ru.rodionov.model.enums.RoleEnum;
import ru.rodionov.service.DoctorService;
import ru.rodionov.util.exception.ServerLogicException;
import ru.rodionov.util.exception.ServerLogicExceptionType;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorFacade {

    private final DoctorService doctorService;
    private final UserMapper userMapper;

    public List<UserDTO> getDoctors(String lastName) {
        return doctorService.getDoctors(lastName).stream().map(userMapper::toDoctorDTO).toList();
    }

    public UserDTO getDoctor(UUID id) {
        User user = doctorService.getDoctor(id);
        if (!RoleEnum.MODERATOR.name().equals(user.getRole().getName())) {
            throw new ServerLogicException("Данный человек не является доктором", ServerLogicExceptionType.VALIDATION_ERROR);
        }
        return userMapper.toDoctorDTO(doctorService.getDoctor(id));
    }
}
