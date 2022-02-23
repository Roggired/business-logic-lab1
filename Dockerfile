FROM openjdk:11 as build
ARG JAR_FILE=build/libs/kickstoper-1.war
WORKDIR /opt/app
COPY ${JAR_FILE} app.war
RUN mkdir -p /uploaded-files
ENTRYPOINT ["java","-jar","app.war"]