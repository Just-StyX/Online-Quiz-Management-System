FROM eclipse-temurin:23-alpine
WORKDIR /app

COPY build/libs/*.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]