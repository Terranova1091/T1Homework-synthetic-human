# 🤖 Synthetic Human Command Processor


## 🚀 Возможности

- Обработка команд с двумя приоритетами: `CRITICAL` и `COMMON`.
- Очередь задач с ограничением по размеру.
- Сбор метрик (`Micrometer + Prometheus`):
  - Текущий размер очереди
  - Счётчик по авторам команд
- Проверка работы через REST API(bishop-prototype).
- Логирование через `Slf4j`.

---

## 📦 Стек технологий

- Java 17
- Spring Boot
- Spring Web
- Micrometer
- Prometheus
- Kafka (опционально для аудита)
- Lombok
- Hibernate Validator (Bean Validation)

---

## ⚙️ Быстрый старт

1. ```git clone https://github.com/your-username/T1Homework-synthetic-human.git```

2. ```Запускаем spring-boot приложение bishop-prototype```

3.  ```Отправляем запрос```
   
Эндпоинт API
POST /api/commands
{
  "description": "Запустить синтез",
  "priority": "COMMON",
  "author": "Лучший проверяющий"
}
📌 Пример запроса можно отправить через Postman.

4*. (Опционально) поднимаем кафку через docker-compose и меняем способ аудита в ```Starter``` с ```CONSOLE``` на ```KAFKA```

Cмотрим результат в терминале + метрики Prometheus(http://localhost:8080/actuator/prometheus)



