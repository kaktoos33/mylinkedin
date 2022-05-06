FROM gradle:7.4.2-jdk8 AS build

MAINTAINER "med.anooshe@gmail.com"

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle clean bootWar -x test -Dspring.profiles.active=lprod

FROM tomcat:8.5

COPY --from=build /home/gradle/src/build/libs/*.war /usr/local/tomcat/webapps/linkedin.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
