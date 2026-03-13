package ru.rodionov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.model.Passport;

import java.util.UUID;

public interface PassportRepository extends JpaRepository<Passport, UUID> {
}
