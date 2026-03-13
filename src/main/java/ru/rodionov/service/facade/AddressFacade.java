package ru.rodionov.service.facade;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodionov.model.Address;
import ru.rodionov.service.AddressService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressFacade {

    private final AddressService addressService;

    @Transactional
    public Address save(Address address) {
        return addressService.save(address);
    }
}
