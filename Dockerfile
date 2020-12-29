FROM maven:3.6.3-openjdk-11 as builder
COPY * ./
RUN mvn package

FROM adoptopenjdk/openjdk11:alpine
EXPOSE 8081
ENV PERFLIB_PROFILE=prod
COPY --from=builder /target/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar"]
