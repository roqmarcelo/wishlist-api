FROM tomcat:9.0-jre11-slim
RUN rm -Rf /usr/local/tomcat/webapps/*
ADD target/*.war /usr/local/tomcat/webapps/ROOT.war