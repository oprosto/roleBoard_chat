# -------- build --------
FROM gradle:8.5-jdk21 AS build

WORKDIR /app
COPY libs libs
COPY chat chat
WORKDIR /app/chat
RUN chmod +x gradlew
RUN ./gradlew clean build -x test

# -------- run --------
FROM eclipse-temurin:21-jdk

WORKDIR /app/chat

COPY --from=build /app/chat/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar", "app.jar"]