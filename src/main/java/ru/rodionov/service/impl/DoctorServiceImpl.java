package ru.rodionov.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rodionov.model.User;
import ru.rodionov.model.enums.RoleEnum;
import ru.rodionov.repository.UserRepository;
import ru.rodionov.service.DoctorService;
import ru.rodionov.util.exception.ServerLogicException;
import ru.rodionov.util.exception.ServerLogicExceptionType;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final UserRepository repository;

    @Override
    public List<User> getDoctors(String lastName) {

        List<User> doctors = repository.findAll().stream()
                .filter(user -> user.getRole().getName().equals(RoleEnum.MODERATOR.getCode().substring(5)))
                .toList();

        if (lastName != null && !lastName.isEmpty()) {
            doctors = doctors.stream()
                    .filter(doctor -> doctor.getLastName().toLowerCase().contains(lastName.toLowerCase()))
                    .toList();
        }

        return doctors;
    }

    @Override
    public User getDoctor(UUID id) {
        return repository.findById(id).orElseThrow(() ->
                new ServerLogicException("Доктор с id = %s не найден".formatted(id), ServerLogicExceptionType.NOT_FOUND));
    }
}
