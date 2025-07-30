# Stage 1: Build with Gradle
FROM gradle:8.5.0-jdk21 AS builder
COPY --chown=gradle:gradle . /build
WORKDIR /build
RUN gradle clean bootJar

# Stage 2: image with JAR
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /build/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
