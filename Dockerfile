# Build Spring Boot application
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY .. .

RUN chmod +x gradlew
RUN ./gradlew --no-daemon clean bootJar -x test

# Run the application
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=55.0", "-jar", "app.jar"]