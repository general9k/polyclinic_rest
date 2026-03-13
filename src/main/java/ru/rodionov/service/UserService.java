package ru.rodionov.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.rodionov.model.User;

import java.util.List;
import java.util.UUID;


public interface UserService {

    List<User> getUsers();

    User getById(UUID id);

    User save(User user);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
