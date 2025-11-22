FROM eclipse-temurin:17-jdk-jammy

MAINTAINER SKCodify

COPY target/myco.jar myco.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "myco.jar"]
