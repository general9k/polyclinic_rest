package ru.rodionov.util;

import org.openapitools.jackson.nullable.JsonNullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JsonNullableHelper {

    public static boolean isPresent(JsonNullable<?> jsonNullable) {
        return jsonNullable != null;
    }

    public static boolean hasValue(JsonNullable<?> jsonNullable) {
        return isPresent(jsonNullable) && jsonNullable.isPresent();
    }

    public static boolean hasValueNotNull(JsonNullable<?> jsonNullable) {
        return hasValue(jsonNullable) && jsonNullable.get() != null;
    }

    public static <T> T getValueOrNull(JsonNullable<T> jsonNullable) {
        return hasValue(jsonNullable) ? jsonNullable.get() : null;
    }

    public static <T, R> R transformIfPresent(JsonNullable<T> jsonNullable, Function<T, R> transformer) {
        if (hasValueNotNull(jsonNullable)) {
            return transformer.apply(jsonNullable.get());
        }
        return null;
    }

    public static <T> T getValueOrSupply(JsonNullable<T> jsonNullable, Supplier<T> supplier) {
        if (hasValue(jsonNullable)) {
            return jsonNullable.get();
        }
        return supplier.get();
    }

    public static <T> void updateField(JsonNullable<T> nullable, Consumer<T> setter) {
        if (JsonNullableHelper.hasValue(nullable)) {
            setter.accept(nullable.get());
        }
    }

}