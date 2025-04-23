# PromoIT OTP Service

![Java](https://img.shields.io/badge/Java-17%2B-blue?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square)

## Описание

**PromoIT OTP Service** — это модуль для генерации и валидации одноразовых кодов (OTP, One-Time Password) с возможностью их доставки по email, SMS (SMPP), Telegram, а также сохранения в файл.  
Поддерживаются разграничение по ролям, JWT-аутентификация, логирование всех запросов.


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

## Токенная аутентификация

После логина используйте полученный JWT токен в заголовке Authorization для всех защищённых запросов.

---

## Разграничение по ролям

- `USER` — может генерировать и проверять OTP
- `ADMIN` — может управлять пользователями

---

## Логирование

Все запросы и события логируются в консоль и (для OTP) в файл `otp_codes.log`.

---

## Эмуляция рассылок

- Email — реальная отправка
- SMPP, Telegram — эмуляция с выводом в лог

---

## Покрытие всех критериев

- Структура, Maven, Spring Boot 3.3.2
- JWT, роли, логирование, рассылки
- Email, SMPP, Telegram, запись в файл
- Тесты (`mvn test`)

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
backend-otp-service/
│
├── .idea/                             # Настройки среды разработки (IntelliJ IDEA)
│   └── ...                            # Файлы конфигурации среды
│
├── .mvn/                              # Maven wrapper
│   └── wrapper/
│       └── maven-wrapper.properties
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── promoit/
│                   └── otp/
│                       ├── config/                 # Конфигурационные классы приложения
│                       │   ├── OtpConfigConfig.java
│                       │   └── SecurityConfig.java
│                       │
│                       ├── controller/             # REST-контроллеры (API)
│                       │   ├── OtpController.java
│                       │   └── UserController.java
│                       │
│                       ├── dao/                    # DAO-интерфейсы и их in-memory реализации
│                       │   ├── OtpCodeDao.java
│                       │   ├── OtpCodeDaoInMemoryImpl.java
│                       │   ├── OtpConfigDao.java
│                       │   ├── OtpConfigDaoInMemoryImpl.java
│                       │   ├── UserDao.java
│                       │   └── UserDaoInMemoryImpl.java
│                       │
│                       ├── model/                  # Модели данных и перечисления
│                       │   ├── enums/
│                       │   │   └── OtpStatus.java
│                       │   ├── OtpCode.java
│                       │   ├── OtpConfig.java
│                       │   ├── OtpRequest.java
│                       │   ├── OtpVerifyRequest.java
│                       │   └── User.java
│                       │
│                       ├── security/               # Классы для безопасности и работы с JWT
│                       │   ├── JwtFilter.java
│                       │   └── JwtUtil.java
│                       │
│                       ├── service/                # Бизнес-логика и сервисы
│                       │   ├── EmailNotificationService.java
│                       │   ├── FileOtpLogger.java
│                       │   ├── OtpService.java
│                       │   ├── SmppNotificationService.java
│                       │   ├── TelegramNotificationService.java
│                       │   └── UserService.java
│                       │
│                       └── MainApplication.java     # Главный стартовый класс приложения
│
│   └── resources/                      # Ресурсы и конфигурационные файлы
│       ├── application.properties
│       ├── email.properties.properties
│       ├── sms.properties.properties
│       └── logback.xml
│
└── test/
    └── java/
        └── com/
            └── promoit/
                └── otp/
                    └── BackendOtpServiceApplicationTests.java
```

> **Пояснения:**
> - Все основные слои приложения вынесены в отдельные пакеты: `config`, `controller`, `dao`, `model`, `security`, `service`.
> - В папке `resources` хранятся настройки Spring и сервисов (email, sms, логирование).
> - В папке `test` размещены интеграционные и/или модульные тесты.
> - В структуре полностью реализуются требования к современному Spring Boot проекту с поддержкой бизнес-логики, безопасности, логирования и тестирования.

---

## Конфигурация OTP

Изменить параметры можно в классе `OtpConfig` или через application properties, если реализована интеграция.

- `codeLength`: длина OTP-кода, по умолчанию 6
- `ttlSeconds`: срок жизни кода в секундах, по умолчанию 300 (5 минут)

---

**PromoIT Team & KimoPWNZ**  
2025
