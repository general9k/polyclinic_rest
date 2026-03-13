package ru.rodionov.service;

import ru.rodionov.model.User;

import java.util.List;
import java.util.UUID;

public interface DoctorService {

    List<User> getDoctors(String lastName);

    User getDoctor(UUID id);

}



