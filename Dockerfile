FROM openjdk:11-jre-slim

WORKDIR /Example


COPY target/*.jar Example.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "Example.jar"]
