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

## Быстрый старт

### 1. Клонирование репозитория

```bash
git clone https://github.com/KimoPWNZ/promoit-otp.git
cd promoit-otp
```

### 2. Конфигурация email

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

> ⚠️ **Обязательно:** Не коммитьте реальные логины и пароли! Используйте переменные окружения или секреты в CI/CD для production.

### 3. Сборка и запуск

```bash
./mvnw spring-boot:run
```

или

```bash
./gradlew bootRun
```

Сервис будет доступен по адресу: [http://localhost:8080](http://localhost:8080)

---

## Примеры API

### Генерация OTP

```http
POST /otp/generate
Content-Type: application/json

{
  "userId": 1,
  "operationId": "login"
}
```

#### Ответ:
```json
{
  "userId": 1,
  "operationId": "login",
  "code": "123456",
  "expiresAt": "2025-04-22T20:00:00",
  "status": "ACTIVE"
}
```

---

### Валидация OTP

```http
POST /otp/verify
Content-Type: application/json

{
  "userId": 1,
  "operationId": "login",
  "code": "123456"
}
```

---

### Регистрация пользователя

```http
POST /users/register
Content-Type: application/json

{
  "login": "user1",
  "passwordHash": "hash123",
  "role": "USER"
}
```

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

## Разработка и вклад

Будем рады любым PR и предложениям!  
Для запуска тестов:

```bash
./mvnw test
```

---

**PromoIT Team & KimoPWNZ**  
2025
