FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY dist/BancoDiarioFacade.war /usr/local/tomcat/webapps/ROOT.war

RUN mkdir -p /usr/local/tomcat/webapps/ROOT

EXPOSE 8080

CMD ["catalina.sh", "run"]