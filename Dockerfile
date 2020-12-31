FROM maven:3.6.3-jdk-11-slim as builder
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package spring-boot:repackage

FROM openjdk:11-jre-slim
COPY --from=builder /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8081
ENV PERFLIB_PROFILE=prod
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
