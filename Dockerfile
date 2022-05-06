FROM gradle:7.4.2-jdk8 AS build

MAINTAINER "med.anooshe@gmail.com"

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle clean bootJar -x test -Dspring.profiles.active=lprod

FROM openjdk:8-jre-slim

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/linkedin.jar

ENTRYPOINT ["java", "-jar","/app/linkedin.jar"]