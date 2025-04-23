# PromoIT OTP Service

![Java](https://img.shields.io/badge/Java-17%2B-blue?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square)

## Описание

**PromoIT OTP Service** — это модуль для генерации и валидации одноразовых кодов (OTP, One-Time Password) с возможностью отправки на e-mail. Поддерживает хранение пользователей и OTP в памяти, а также гибкую конфигурацию длительности жизни и длины кода. Сервис легко интегрируется в любые приложения, требующие двухфакторной аутентификации или подтверждения операций.

---

## Основные возможности

- Генерация одноразовых кодов (OTP) заданной длины и времени жизни (TTL)
- Валидация OTP для пользователя и операции
- Отправка OTP на e-mail (через SMTP)
- Управление пользователями (регистрация, аутентификация, удаление, просмотр)
- Гибкая конфигурация через properties
- Простой in-memory DAO для демонстрационных и тестовых целей

---

## Технологии

- Java 17+
- Spring Boot 3.x
- Jakarta Mail
- Lombok

---

### Запуск

1. **Клонируйте репозиторий:**
    ```bash
    git clone https://github.com/KimoPWNZ/promoit-otp.git
    cd promoit-otp
    ```

2. **Добавьте файл конфигурации email:**  
   Создайте файл `src/main/resources/email.properties` со следующим содержимым:
    ```properties
    email.username=your@email.com
    email.password=your_email_password
    email.from=your@email.com
    mail.smtp.host=smtp.yourprovider.com
    mail.smtp.port=465
    mail.smtp.auth=true
    mail.smtp.ssl.enable=true
    ```

3. **Соберите и запустите проект:**
    ```bash
    ./mvnw spring-boot:run
    ```
    или
    ```bash
    ./gradlew bootRun
    ```

---

## Какие команды поддерживаются

API реализован в виде HTTP-эндпоинтов (примерные адреса):

- **Генерация OTP**  
  `POST /otp/generate`  
  Тело запроса:
    ```json
    {
      "userId": 1,
      "operationId": "login"
    }
    ```

- **Проверка OTP**  
  `POST /otp/verify`  
  Тело запроса:
    ```json
    {
      "userId": 1,
      "operationId": "login",
      "code": "123456"
    }
    ```

- **Регистрация пользователя**  
  `POST /users/register`  
  Тело запроса:
    ```json
    {
      "login": "user1",
      "passwordHash": "hash123",
      "role": "USER"
    }
    ```

- **Получение всех пользователей**  
  `GET /users/all`

- **Удаление пользователя**  
  `DELETE /users/{login}`

---

## Как протестировать код

1. **Автоматические тесты**
    ```bash
    ./mvnw test
    ```
    или
    ```bash
    ./gradlew test
    ```

2. **Проверка вручную через Postman, Curl или Swagger UI**
    - Проверьте регистрацию пользователя.
    - Проверьте генерацию OTP.
    - Проверьте получение и валидацию OTP.
    - Проверьте отправку OTP на email.

---

## Структура проекта

```
src/
 ├─ main/
 │   ├─ java/com/promoit/otp/
 │   │    ├─ model/          // Модели данных (User, OtpCode, OtpConfig, ...)
 │   │    ├─ dao/            // In-memory DAO для пользователей
 │   │    ├─ service/        // Сервисы бизнес-логики (OtpService, UserService, EmailNotificationService)
 │   │    └─ controller/     // REST-контроллеры (если есть)
 │   └─ resources/
 │        └─ email.properties // Конфиг для SMTP
 └─ test/
```

---

## Конфигурация OTP

Изменить параметры можно в классе `OtpConfig` или через application properties, если реализована интеграция.

- `codeLength`: длина OTP-кода, по умолчанию 6
- `ttlSeconds`: срок жизни кода в секундах, по умолчанию 300 (5 минут)

---

**PromoIT Team & KimoPWNZ**  
2025
