FROM openjdk:11.0.12-jre
MAINTAINER Buon SUI <boonsuli@gmail.com>
VOLUME /tmp
RUN mkdir -p /app/
WORKDIR /app
COPY ./target/trailerplan-race-api.jar /app/trailerplan-race-api.jar
ENTRYPOINT ["/usr/local/openjdk-11/bin/java", "-Dspring.profiles.active=dev-app-container", "-jar", "/app/trailerplan-race-api.jar"]
