# Client Management System for Recruitment Process

## Overview

This microservice, built with **Java 21**, **Spring Boot**, and **MySQL**, provides an API to manage client data during a recruitment process. The system is designed to be **scalable, secure, portable, and cloud-ready**, following best practices in software architecture and backend development.

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- Spring Validation
- Springdoc OpenAPI (Swagger)
- Flyway (DB Migrations)
- JSON Web Tokens (JWT)
- MapStruct
- Lombok
- MySQL
- Gradle
- Docker

## Key Features

- 📥 Register new clients (name, last name, age, birthdate)
- 📋 Retrieve all clients with derived data (e.g., life expectancy estimate)
- 📊 Age metrics: average and standard deviation
- 🔐 JWT-based authentication and authorization
- 🛠 Input validation and centralized exception handling
- 📚 Auto-generated API docs with Swagger
- 🐘 DB schema versioning with Flyway
- 📈 Actuator endpoints for metrics and health checks

## Configuration

### Required Environment Variables

```env
DB_HOST=localhost
DB_PORT=3306
DB_NAME=clientes_db
DB_USERNAME=username
DB_PASSWORD=password
````

## Running the App

### Using Gradle

```bash
./gradlew clean build -x test
java -jar build/libs/seek-client-ms-*.jar
```

### Using Docker

#### Multi-stage Dockerfile

```dockerfile
# Stage 1: Build
FROM gradle:8.5.0-jdk21 AS builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle clean build -x test

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /app/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

#### Build and Run with Docker

```bash
docker build -t seek-client-ms .
docker run -e DB_HOST=host -e DB_PORT=3306 -e DB_NAME=clientes_db \
  -e DB_USERNAME=username -e DB_PASSWORD=password \
  -p 8080:8080 seek-client-ms
```

## API Endpoints

| Method | Endpoint               | Description                                      |
| ------ | ---------------------- | ------------------------------------------------ |
| POST   | `/api/clients`         | Register a new client                            |
| GET    | `/api/clients`         | Get all registered clients                       |
| GET    | `/api/clients/metrics` | Get average and standard deviation of client age |

* Swagger UI: `http://localhost:8080/swagger-ui.html`
* API Docs (OpenAPI): `/api-docs`

## Testing

Includes a Postman collection (`.postman_collection.json`) with test cases for:

* ✅ Successful request (HTTP 200)
* ❌ Server error (HTTP 500)
* ⚠️ Validation/business error (HTTP 422)

## Main Dependencies (`build.gradle`)

- Spring Boot Web Starter
- Spring Data JPA
- Spring Security
- Spring Validation
- Springdoc OpenAPI UI Starter
- Flyway Core
- Flyway MySQL Support
- JSON Web Token (JJWT)
- MapStruct
- MySQL Connector/J
- Lombok
- Spring Boot Test Starter
- Spring Security Test

## Deployment

This service is ready to be deployed on:

* AWS ECS / Fargate
* AWS EC2

Make sure to use externalized environment variables and a managed MySQL instance.

## Author

**\[Boris Muriel]**
Fullstack Developer Architect
📧 [andremur98@gmail.com](mailto:your.email@example.com)
🔗 [https://github.com/BoriSAMA](https://github.com/BoriSAMA)
