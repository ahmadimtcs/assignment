FROM openjdk:11-jre-slim
WORKDIR /app

COPY ./build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8080