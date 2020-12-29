FROM maven:3.6.3-openjdk-11
COPY * ./
RUN mvn package

FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=target/*.jar
EXPOSE 8081
ENV PERFLIB_PROFILE=prod
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar"]
