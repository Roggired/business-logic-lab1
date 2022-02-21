FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=build/libs/kickstoper-1.war
WORKDIR /opt/app
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","app.war"]