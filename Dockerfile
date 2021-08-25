FROM openjdk:8-jre-alpine
WORKDIR /app
COPY build/libs/weather-0.0.1-SNAPSHOT.jar weather-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/app/weather-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081

