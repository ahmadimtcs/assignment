FROM openjdk:11-jre-slim
WORKDIR /app
COPY build/libs/whetherapp-0.0.1-SNAPSHOT.jar whetherapp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/app/whetherapp-0.0.1-SNAPSHOT.jar"]
EXPOSE 8090



