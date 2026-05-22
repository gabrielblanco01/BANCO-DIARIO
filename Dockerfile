FROM tomcat:10.1-jdk17

# Limpiar apps por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiar el WAR compilado al directorio de Tomcat
COPY dist/BancoDiarioFacade.war /usr/local/tomcat/webapps/ROOT.war

# Puerto que Railway espera
EXPOSE 8080

# Arrancar Tomcat
CMD ["catalina.sh", "run"]
