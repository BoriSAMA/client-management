# Stage 1: Build with Gradle
FROM gradle:8.5.0-jdk21 AS builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle clean build -x test

# Stage 2: image with JAR
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /app/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
