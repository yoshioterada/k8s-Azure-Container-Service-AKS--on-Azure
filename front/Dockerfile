#####################################################
# Build & Test & Create an artifact
#####################################################
FROM maven:3.5-jdk-8 as BUILD
LABEL MAINTAINER Yoshio Terada

COPY src /usr/src/myapp/src
COPY pom.xml /usr/src/myapp

RUN mvn -f /usr/src/myapp/pom.xml clean package


#####################################################
# Build container image copying from BUILD artifact
#####################################################
FROM payara/micro:5.182
LABEL MAINTAINER Yoshio Terada


COPY --from=BUILD /usr/src/myapp/target/*.war $DEPLOY_DIR/ROOT.war
#COPY target/*.war $DEPLOY_DIR/ROOT.war
EXPOSE 8080
