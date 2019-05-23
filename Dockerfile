FROM openjdk:8-jdk-slim

RUN mkdir -p /masterService

WORKDIR /masterService

#TODO add valid build path
ADD target/app-0.0.1-SNAPSHOT.jar /masterService/

RUN chown 10001:10001 -R /masterService

USER 10001

#TODO add vlid profile
#ENTRYPOINT java -jar /masterService/*-$APP_VERSION.jar --spring.profiles.active=${SPRING_PROFILE}
ENTRYPOINT java -jar /masterService/app-0.0.1-SNAPSHOT.jar