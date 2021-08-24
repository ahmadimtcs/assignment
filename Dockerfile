FROM openjdk:8-jre-alpine
WORKDIR /app
COPY build/libs/*.jar weather-app-image.jar
ENTRYPOINT ["java", "-jar","/app/weather-app-image.jar"]
EXPOSE 8081

