FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/mailService-1.war
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]