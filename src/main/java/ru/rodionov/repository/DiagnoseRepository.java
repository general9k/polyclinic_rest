package ru.rodionov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.model.Diagnose;

import java.util.UUID;

public interface DiagnoseRepository extends JpaRepository<Diagnose, UUID> {

}
