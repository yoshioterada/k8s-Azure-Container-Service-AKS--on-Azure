#####################################################
# Build & Test & Create an artifact
#####################################################
FROM maven:3.5-jdk-8 as BUILD
LABEL MAINTAINER Yoshio Terada

COPY hazelcast-default.xml /usr/src/myapp/hazelcast-default.xml
COPY src /usr/src/myapp/src
COPY pom.xml /usr/src/myapp
RUN mvn -f /usr/src/myapp/pom.xml clean package

#####################################################
# Build container image copying from BUILD artifact
#####################################################
# FROM java:8-jdk-alpine
FROM openjdk:8-jre-alpine 
LABEL MAINTAINER Yoshio Terada

COPY --from=BUILD /usr/src/myapp/hazelcast-default.xml /tmp
COPY --from=BUILD /usr/src/myapp/target/payara-micro.jar /tmp
COPY --from=BUILD /usr/src/myapp/target/AccountSvc-1.0-SNAPSHOT.war /tmp/app.war

RUN addgroup -g 1000 java
RUN adduser -D -u 1000 -G java java
RUN chown java:java /tmp/payara-micro.jar
RUN chmod 755 /tmp/payara-micro.jar

USER java

EXPOSE 8080
ENTRYPOINT java -jar /tmp/payara-micro.jar --hzconfigfile /tmp/hazelcast-default.xml --deploy /tmp/app.war
