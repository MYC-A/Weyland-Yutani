# Weyland-Yutani: Объединённый проект
# Weyland-Yutani: Weather Kafka Application

**Weyland-Yutani** — это объединённый проект, объединяющий два модуля: `synthetic-core-starter` (библиотека для работы с Kafka и AOP) и `bishop-prototype` (приложение для генерации и анализа данных о погоде с использованием Apache Kafka). Проект демонстрирует интеграцию Spring Boot с Kafka для обработки задач через REST API и событийный поток, с мониторингом через Actuator и Micrometer.

## Структура проекта

- **`synthetic-core-starter/`**: Библиотека-стартер с общей логикой для работы с Kafka, AOP-аннотациями и сервисами обработки задач.
- **`bishop-prototype/`**: Основное приложение, использующее `synthetic-core-starter` для обработки погодных данных через REST API и Kafka.
- **`docker-compose.yml`**: Конфигурация для запуска Zookeeper, Kafka и приложения в Docker.

## Технологии

- **Spring Boot 3**: Основа для REST API и интеграции с Kafka.
- **Apache Kafka**: Для асинхронной обработки и передачи данных.
- **Spring AOP**: Для аудита.
- **Actuator & Micrometer**: Для мониторинга и сбора метрик.
- **Docker & Docker Compose**: Для контейнеризации и оркестрации.

## Требования

- **Docker Desktop** (Windows/Mac) или **Docker Engine** (Linux)
- **Docker Compose** v2.0 или выше
- **Оперативная память**: 4GB+ свободной

## Установка и запуск

### 1. Склонируйте репозиторий
```bash
git clone https://github.com/MYC-A/Weyland-Yutani.git
cd Weyland-Yutani
```

### 2. Соберите проекты
Модуль `bishop-prototype` зависит от `synthetic-core-starter`. Сначала соберите стартер, затем приложение:

```bash
cd synthetic-core-starter
mvn clean install
cd ../bishop-prototype
mvn clean package
```

### 3. Запустите сервисы
В корне репозитория находится `docker-compose.yml`, который запускает Zookeeper, Kafka и приложение `bishop-prototype`:

```bash
cd ..
docker-compose up --build -d
```
- Флаг `--build` пересобирает Docker-образы.
- Флаг `-d` запускает контейнеры в фоновом режиме.

## Использование

### Отправка задачи через REST API
Приложение `bishop-prototype` предоставляет эндпоинт `/api/tasks` для отправки задач в Kafka. Используйте следующий JSON-формат:

```json
{
  "description": "Тестовая задача 1",
  "priority": "COMMON",
  "author": "Alice",
  "time": "2025-07-19T12:00:00"
}
```

Пример запроса:
```bash
curl -X POST http://localhost:8080/api/tasks \
-H "Content-Type: application/json" \
-d '{"description":"Тестовая задача 1","priority":"COMMON","author":"Alice","time":"2025-07-19T12:00:00"}'
```

**Ожидаемый ответ**:
```json
"Задача добавлена в очередь"
```

**Пример лога**:
```
2025-07-20T09:38:45.433Z  INFO 1 --- [bishop-prototype] [pool-2-thread-1] c.w.starter.service.CommandServiceImpl   : Выполняется команда: description=Тестовая задача 1, priority=COMMON, author=Alice, time=2025-07-19T12:00:00
```

## Мониторинг и метрики

Приложение использует **Spring Boot Actuator** и **Micrometer** для мониторинга. Доступные метрики включают:

- `http.server.requests`: Статистика HTTP-запросов (например, к `/api/tasks`).
- `synthetic.queue.size`: Размер очереди задач
- `spring.kafka.template`: Метрики операций Kafka (отправка сообщений).
- `jvm.memory.used`: Использование памяти JVM.
- `jvm.gc.pause`: Время пауз сборщика мусора.

### Проверка метрик
Получите список метрик:
```bash
curl http://localhost:8080/actuator/metrics
```

```bash
curl http://localhost:8080/actuator/metrics/http.server.requests
```

## Структура модулей

### synthetic-core-starter
- **Роль**: Библиотека с общей логикой.
- **Компоненты**:
  - `CommandDTO`: DTO для задач
  - `CommandService`: Сервис для отправки задач в Kafka.
  - `AuditService`: Логирование через AOP.

### bishop-prototype
- **Роль**: Основное приложение.
- **Компоненты**:
  - REST API: Эндпоинт `/api/tasks` для отправки задач.
  - Интеграция с Kafka: Отправка и обработка сообщений в `audit-topic`.
  - Мониторинг: Actuator и Micrometer.

