FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/restservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} restservice.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/restservice.jar"]