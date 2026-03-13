package ru.rodionov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String login);

}
