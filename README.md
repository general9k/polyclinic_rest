# REST API для автоматизации работы частной медицинской клиники.

## Описание предметной области

Private Clinic REST API — это бэкенд-сервис для автоматизации работы частной медицинской клиники. 
Система позволяет управлять записями пациентов на приём, вести электронные медицинские карты, назначать диагнозы и лекарства.

📅 Управление приёмами (Receptions)
- ✅ Запись пациента к врачу с указанием даты и времени
- ✅ Назначение симптомов, диагнозов и лекарств в рамках приёма
- ✅ Формирование электронного рецепта (prescription)
- ✅ Частичное обновление данных приёма (PATCH)
- ✅ Отмена записи (удаление приёма)

👤 Управление пользователями
- ✅ Профили пациентов и врачей с паспортными данными и адресом
- ✅ Частичное обновление профиля (имя, контакты, документы)
- ✅ Разделение ролей: ROLE_ADMIN, ROLE_MODERATOR, ROLE_USER

👨‍⚕️ Справочники медицины
- ✅ Диагнозы: создание, просмотр, удаление (с защитой от удаления при наличии связей)
- ✅ Симптомы: управление справочником симптомов
- ✅ Лекарства: полное управление (CRUD) с описанием способа применения и побочных эффектов

🔍 Поиск и фильтрация
- ✅ Фильтрация врачей по фамилии
- ✅ Фильтрация лекарств по названию
- ✅ Гибкая сортировка и пагинация (реализуется на уровне сервиса)

🔐 Безопасность и валидация
- ✅ JWT-аутентификация с настраиваемым временем жизни токена
- ✅ Валидация входных данных: @NotNull, @Size, @Length, @Pattern
- ✅ Единый формат обработки ошибок

## Стек технологий

- ЯП: Java 21
- Фреймворк: Spring Boot 3.4.3
- Миграции: Liquibase
- ORM: Spring Data JPA + Hibernate
- Безопасность: Spring Security + JWT (jjwt 0.11.5)
- Валидация: Jakarta Validation + Hibernate Validator
- Маппинг: MapStruct 1.5.5.Final
- JSON: Jackson + jackson-databind-nullable
- Утилиты: Lombok
- Сборка: Maven 3.9+

## Как запустить проект:

1. Требования
- Java 21 SDK
- Maven 3.9+
- PostgreSQL 15+
- Docker

## Настройка БД

```bash
docker run -d \
  --name abitur-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=polyclinic \
  -p 5432:5432 \
  postgres:15-alpine
```

## Конфигурация

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/polyclinic
    username: postgres
    password: postgres
  security:
    jwt:
      secret-key: your-secret-key-here  # Замените на свой!
      expiration-ms: 86400000  # 24 часа
```

## Сборка и запуск

```bash
# Скомпилировать проект
mvn clean compile

# Запустить приложение
mvn spring-boot:run

# Или собрать JAR и запустить
mvn clean package
java -jar target/polyclinic-0.0.1-SNAPSHOT.jar
```

## Список маршрутов API

Сваггер по ссылке: http://localhost:8080/swagger-ui/index.html#/

