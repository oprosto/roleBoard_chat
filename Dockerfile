# -------- build --------
FROM gradle:8.5-jdk21 AS build

WORKDIR /app
COPY libs libs
COPY chat-service chat-service
WORKDIR /app/chat-service
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

# -------- run --------
FROM eclipse-temurin:21-jdk

WORKDIR /app/chat-service

COPY --from=build /app/chat-service/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar", "app.jar"]