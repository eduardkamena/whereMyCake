# WhereMyCake 🍰

**WhereMyCake** — это мини-приложение для управления дневной нормой калорий и отслеживания питания пользователя. Оно позволяет:
- Рассчитывать дневную норму калорий на основе данных пользователя (пол, вес, рост, возраст).
- Добавлять блюда и их параметры (калории, белки, жиры, углеводы).
- Получать отчеты о питании за день или за период.
- Сравнивать фактическое потребление калорий с дневной нормой.

---

## Технологии 🛠️

- **Backend**: Java 17, Spring Boot 3.4.3
- **База данных**: PostgreSQL
- **Миграции**: Liquibase
- **Документация API**: OpenAPI 3.1.0 (Swagger)
- **Тестирование**: Postman, JUnit 5, Mockito
- **Сборка**: Maven

---

## Запуск проекта 🚀

### Требования
- Установленная Java 17 или выше.
- Установленная PostgreSQL.
- Установленный Maven.

### Шаги для запуска

1. **Клонируйте репозиторий**:
   ```bash
   git clone https://github.com/eduardkamena/whereMyCake.git
   cd whereMyCake
   ```

2. **Настройте базу данных:**:
- Создайте базу данных в PostgreSQL:
```sql
CREATE DATABASE whereMyCake;
```
- Настройте подключение к базе данных в файле `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/whereMyCake
spring.datasource.username=USER
spring.datasource.password=PASSWORD
```

3. **Запустите приложение:**
- Соберите проект с помощью Maven:
```bash
mvn clean install
```
- Запустите приложение:
```bash
mvn spring-boot:run
```

4. **Документация API:**
- После запуска приложения откройте Swagger UI по адресу:
```
http://localhost:8080/swagger-ui.html
```

## API Endpoints 🌐

### UserController

- **POST /users** — Создание нового пользователя.
- Пример запроса:
```json
{
   "name": "Иван Вкомандухотелов",
   "email": "ivan@mail.ru",
   "gender": "М",
   "age": 25,
   "weight": 70,
   "height": 180,
   "aim": "Похудение"
}
```

### DishController

- **POST /dishes/{userId}** — Добавление списка блюд для пользователя.
- Пример запроса:
```json
[
   {
      "title": "Салат Цезарь",
      "parameters": [
         {
            "calorie": 250,
            "protein": 10,
            "fat": 15,
            "carb": 20
         }
      ]
   }
]
```

### CalorieController

- **GET /calorie/{userId}** — Получение дневной нормы калорий для пользователя.

### ReportController

- **GET /reports/daily/total** — Получение дневного отчета по калориям.
- **GET /reports/daily/check** — Проверка дневной нормы калорий.
- **GET /reports/history** — Получение истории питания за период.

## Тестирование 🧪

### Postman

- Импортируйте коллекцию Postman из файла `postman_collection.json`.
- Убедитесь, что переменные окружения (`base_url, user_id, date, start_date, end_date`) настроены корректно.
- **Обязательно!** Измените даты `date, start_date, end_date`, начиная с текущей.
- Запустите тесты для проверки работы API.

### Unit-тесты

- Запустите unit-тесты с помощью Maven:
```bash
mvn test
```

## Автор 👥

- **Эдуард Каменских** — [eduardkamena](https://github.com/eduardkamena)

## Проблемы и отзывы 🙏

Если вы столкнулись с какой-то проблемой или у вас есть предложения, пожалуйста, напишите в [GitHub Issues](https://github.com/eduardkamena/whereMyCake/issues).