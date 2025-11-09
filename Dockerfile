FROM openjdk:17-jdk-slim

MAINTAINER SKCodify

COPY target/myco-0.0.1-SNAPSHOT.jar myco-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "myco-0.0.1-SNAPSHOT.jar"]

