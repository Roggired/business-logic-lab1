FROM openjdk:11 as build
ARG JAR_FILE=build/libs/kickstoper-1.war
WORKDIR /opt/app
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","app.war"]