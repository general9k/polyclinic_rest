package ru.rodionov.service.facade;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.model.Passport;
import ru.rodionov.service.PassportService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PassportFacade {

    private final PassportService passportService;

    @Transactional
    public Passport save(Passport passport) {
        return passportService.save(passport);
    }
}
