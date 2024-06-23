FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get install -y curl
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/*-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

# Run the JAR file
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]