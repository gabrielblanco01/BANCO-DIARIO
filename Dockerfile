FROM eclipse-temurin:25-jdk AS builder

FROM tomcat:11.0-jdk21

RUN rm -rf /usr/local/tomcat/webapps/*

COPY dist/BancoDiarioFacade.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
