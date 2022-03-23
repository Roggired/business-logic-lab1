FROM openjdk:8-jdk
ARG JAR_FILE=build/libs/bank-1.war
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]