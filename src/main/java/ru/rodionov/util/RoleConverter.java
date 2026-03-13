package ru.rodionov.util;


import ru.rodionov.model.enums.RoleEnum;
import ru.rodionov.util.interfaceEnum.UniversalEnumConverter;

public class RoleConverter extends UniversalEnumConverter<RoleEnum> {

    public RoleConverter() {
        super(RoleEnum.class);
    }
}
