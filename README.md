# Информационная подсистема управления региональной сетью (kursach_stas)

Веб-приложение на Java EE для управления региональной сетью, филиалами и представителями.

## Описание

Система позволяет управлять:
- **Регионами** - информация о регионах (название, код региона, куратор, контакты)
- **Филиалами** - учет филиалов в регионах
- **Представителями** - информация о представителях компании
- **Отчетами представителей** - учет отчетов о работе представителей

## Технологии

- **Java 17+**
- **Jakarta EE 9**
- **JSF 3.0** (Mojarra)
- **PrimeFaces 12.0**
- **Hibernate 6.0**
- **PostgreSQL**
- **CDI (Weld)**
- **Apache Tomcat 10**

## Требования

- Java 17 или выше
- Maven 3.6+
- PostgreSQL 12+

## Установка и настройка

### 1. Настройка базы данных PostgreSQL

Запустите скрипт настройки БД:

```bash
./setup-db.sh
```

Или создайте базу данных вручную:

```sql
CREATE DATABASE kursach_stas;
CREATE USER project_role WITH PASSWORD 'project_role';
GRANT ALL PRIVILEGES ON DATABASE kursach_stas TO project_role;
```

### 2. Сборка проекта

```bash
mvn clean package
```

Это создаст WAR файл в директории `target/kursach.war`.

### 3. Запуск приложения

#### Вариант 1: Использование скриптов запуска (рекомендуется)

**Запуск:**

```bash
./start.sh
```

Скрипт автоматически:
- Проверит подключение к PostgreSQL
- Соберет проект (mvn clean package)
- Скопирует WAR файл в Tomcat
- Запустит Tomcat

**Остановка:**

```bash
./stop.sh
```

#### Вариант 2: Ручной запуск через Tomcat

Проект уже содержит Apache Tomcat 10 в директории `apache-tomcat-10/`.

**Запуск:**

```bash
# Сборка проекта
mvn clean package

# Копирование WAR в Tomcat
cp target/kursach.war apache-tomcat-10/webapps/

# Запуск Tomcat
./apache-tomcat-10/bin/startup.sh
```

**Остановка:**

```bash
./apache-tomcat-10/bin/shutdown.sh
```

#### Вариант 3: Использование Jetty (для разработки)

```bash
mvn jetty:run
```

Приложение будет доступно по адресу: http://localhost:8081/kursach/

### 4. Инициализация данных

При первом запуске приложения Hibernate автоматически создаст таблицы на основе JPA сущностей.

## Конфигурация

### База данных

Настройки подключения к БД находятся в:
- `src/main/resources/META-INF/persistence.xml` - для JPA/Hibernate
- `src/main/webapp/META-INF/context.xml` - для Tomcat DataSource

**Текущие настройки:**
- База данных: `kursach_stas`
- Пользователь: `project_role`
- Пароль: `project_role`
- Хост: `localhost:5432`

## Структура проекта

```
src/
├── main/
│   ├── java/
│   │   └── org/kursach/kursach/
│   │       ├── config/          # Конфигурация (CDI, JPA, Security)
│   │       ├── controller/      # JSF контроллеры
│   │       ├── converter/       # JSF конвертеры
│   │       ├── model/           # JPA сущности (Region, BranchOffice, Representative, RepresentativeReport)
│   │       ├── repository/      # Репозитории для работы с БД
│   │       ├── security/        # Безопасность и аутентификация
│   │       └── service/         # Бизнес-логика
│   ├── resources/
│   │   └── META-INF/
│   │       └── persistence.xml  # Конфигурация JPA
│   └── webapp/
│       ├── *.xhtml              # JSF страницы
│       ├── META-INF/
│       │   └── context.xml      # Конфигурация Tomcat
│       └── WEB-INF/
│           ├── web.xml          # Конфигурация веб-приложения
│           └── faces-config.xml # Конфигурация JSF
```

## Доступ к приложению

После запуска приложение будет доступно по адресу:
- http://localhost:8080/kursach/ (Tomcat)
- http://localhost:8081/kursach/ (Jetty)

**Учетные данные для входа:**
- Логин: `admin`
- Пароль: `admin`

## Примечания

- База данных автоматически создается при первом запуске благодаря настройке `hibernate.hbm2ddl.auto=update`
- Для работы требуется запущенный PostgreSQL сервер
# kursa-region-representative
"# kursa" 
