# Random Quote Web Service

This project is a web service designed to deliver random quotes. When accessing the main API endpoint, a random quote, retrieved from an external 
source  [Quotable](https://api.quotable.io/random), is presented. Each request returns a different random quote.

Implementations and approaches applicable to production-ready projects were utilized in this project.

Beyond the core feature of delivering random quotes, additional functionality has been implemented.

Установка и запуск может зависеть от среды и настройки проекта, но общая структура может выглядеть следующим образом:

## Installation and Running

### Required Tools
- [Java](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) version 17 or higher
- [Gradle](https://gradle.org/install/) version 7.0 or higher

### Running

1. Build the project:

```bash
./gradlew build
```

2. Run the application:

```bash
./gradlew bootRun
```

The application will start and by default can be accessed at http://localhost:8080.

### Running Tests

Tests can be run by executing:

```bash
./gradlew test
```

This will run all unit tests in the project and report the results.

> **Note:** Replace the repository URL and project directory with the actual repository URL and project directory.

## Usage

* Java 17
* Spring Boot
* Spring WebFlux
* Gradle
* REST
