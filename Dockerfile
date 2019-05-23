FROM openjdk:8-jdk-slim

RUN mkdir -p /masterService

WORKDIR /masterService

ADD ./build/libs/app*.jar /masterService/

RUN chown 10001:10001 -R /masterService

USER 10001

ENTRYPOINT java -jar /masterService/*-$APP_VERSION.jar --spring.profiles.active=${SPRING_PROFILE}