package ru.rodionov.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rodionov.model.User;
import ru.rodionov.model.enums.RoleEnum;
import ru.rodionov.repository.UserRepository;
import ru.rodionov.security.details.AuthUserDetails;
import ru.rodionov.service.UserService;
import ru.rodionov.util.exception.ServerLogicException;
import ru.rodionov.util.exception.ServerLogicExceptionType;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll().stream().filter(user -> RoleEnum.USER.name().equals(user.getRole().getName())).toList();
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ServerLogicException("Пользователь с таким id = %s не найден".formatted(id), ServerLogicExceptionType.NOT_FOUND));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Попытка загрузки пользователя с логином: {}", username);
        return userRepository.findByUsername(username)
                .map(AuthUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Авторизированный пользователь с таким %s не найден", username)));
    }
}