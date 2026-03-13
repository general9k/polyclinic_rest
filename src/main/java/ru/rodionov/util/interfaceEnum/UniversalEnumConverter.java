package ru.rodionov.util.interfaceEnum;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;

public class UniversalEnumConverter<E extends Enum<E>> implements AttributeConverter<E, String> {

    private final Class<E> enumType;

    public UniversalEnumConverter(Class<E> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name().toLowerCase();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Arrays.stream(enumType.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown value: " + dbData));
    }
}
