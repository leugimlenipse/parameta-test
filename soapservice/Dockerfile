FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/soapservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} soapservice.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/soapservice.jar"]