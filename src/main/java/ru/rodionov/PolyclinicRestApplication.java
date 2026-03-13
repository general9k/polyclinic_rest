package ru.rodionov;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


@EnableJpaRepositories(basePackages = {"ru.rodionov.repository"})
@EntityScan(basePackages = {"ru.rodionov.model"})
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@SpringBootApplication
public class PolyclinicRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolyclinicRestApplication.class, args);
    }

    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS));
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = builder
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
        mapper.registerModule(new JsonNullableModule());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
