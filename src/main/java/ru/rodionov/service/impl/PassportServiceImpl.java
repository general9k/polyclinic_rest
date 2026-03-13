package ru.rodionov.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rodionov.model.Passport;
import ru.rodionov.repository.PassportRepository;
import ru.rodionov.service.PassportService;

@Service
@AllArgsConstructor
@Slf4j
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;

    @Override
    public Passport save(Passport passport) {
        return passportRepository.save(passport);
    }
}
