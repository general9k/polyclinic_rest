package ru.rodionov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rodionov.model.Address;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
