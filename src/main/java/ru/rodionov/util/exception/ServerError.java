package ru.rodionov.util.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Schema(
        name = "ServerError",
        description = "Информация о возникшей технической ошибке"
)
public class ServerError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private CodeEnum code;
    private String message;

    public ServerError() {
    }

    public ServerError(CodeEnum code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerError code(CodeEnum code) {
        this.code = code;
        return this;
    }

    @NotNull
    @Schema(
            name = "code",
            description = "Тип возникшей ошибки",
            requiredMode = RequiredMode.REQUIRED
    )
    @JsonProperty("code")
    public @NotNull CodeEnum getCode() {
        return this.code;
    }

    public ServerError message(String message) {
        this.message = message;
        return this;
    }

    @Schema(
            name = "message",
            description = "Детализация возвращенной ошибки",
            requiredMode = RequiredMode.REQUIRED
    )
    @JsonProperty("message")
    public @NotNull String getMessage() {
        return this.message;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ServerError serverError = (ServerError) o;
            return Objects.equals(this.code, serverError.code) && Objects.equals(this.message, serverError.message);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.code, this.message});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ServerError {\n");
        sb.append("    code: ").append(this.toIndentedString(this.code)).append("\n");
        sb.append("    message: ").append(this.toIndentedString(this.message)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }

    public static enum CodeEnum {
        VALIDATION_ERROR("VALIDATION_ERROR"),
        AUTHENTICATION_ERROR("AUTHENTICATION_ERROR"),
        ACCESS_DENIED("ACCESS_DENIED"),
        ENTITY_NOT_FOUND("ENTITY_NOT_FOUND"),
        ENTITY_ALREADY_EXISTS("ENTITY_ALREADY_EXISTS"),
        METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED"),
        ENTITY_ACCESS_CONFLICT("ENTITY_ACCESS_CONFLICT"),
        UNSUPPORTED_MEDIA_TYPE("UNSUPPORTED_MEDIA_TYPE"),
        ENTITY_INVARIABLE("ENTITY_INVARIABLE"),
        DATABASE_REQUEST_EXCEPTION("DATABASE_REQUEST_EXСEPTION"),
        SERVICE_CALL_ERROR("SERVICE_CALL_ERROR"),
        SERVICE_ERROR("SERVICE_ERROR"),
        NOT_ACCEPTABLE("NOT_ACCEPTABLE"),
        DATABASE_REQUEST_EX_EPTION("DATABASE_REQUEST_EXСEPTION");

        private final String value;

        private CodeEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }

        @JsonCreator
        public static CodeEnum fromValue(String value) {
            CodeEnum[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                CodeEnum b = var1[var3];
                if (b.value.equals(value)) {
                    return b;
                }
            }

            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}
