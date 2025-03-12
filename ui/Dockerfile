#FROM openjdk:17-jdk-alpine
#FROM registry.access.redhat.com/ubi8/openjdk-17
FROM registry.access.redhat.com/ubi8/openjdk-21-runtime
LABEL maintainer="domenico@piol.name"

# WORKDIR is /home/jboss by default!!!

USER root
#RUN microdnf update -y && rm -rf /var/cache/yum
#RUN microdnf install wget -y && microdnf clean all
COPY target/sccstore-ui-0.0.1-SNAPSHOT.jar sccstore-ui.jar
RUN chgrp -R 0 /home/jboss && chmod -R g=u /home/jboss && chmod 777 sccstore-ui.jar
USER 185

ENTRYPOINT ["java","-jar","/home/jboss/sccstore-ui.jar"]