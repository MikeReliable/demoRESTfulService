FROM openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY target/*.jar /app/demoRESTfulService.jar
ENTRYPOINT ["java", "-jar", "/app/demoRESTfulService.jar"]