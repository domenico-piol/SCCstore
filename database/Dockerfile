FROM openjdk:17-jdk-alpine
MAINTAINER domenico@piol.name

RUN apk add --no-cache curl

ENV H2_VERSION 2.0.202
ENV H2_DATA /h2-data
ENV H2_WEBPORT 8082
ENV H2_TCPPORT 9092
ENV DOWNLOAD_URL https://repo1.maven.org/maven2/com/h2database/h2/${H2_VERSION}/h2-${H2_VERSION}.jar

RUN mkdir -p ${H2_DATA} && curl ${DOWNLOAD_URL} -o ${H2_DATA}/h2.jar

EXPOSE 8082 9092

WORKDIR ${H2_DATA}
ENTRYPOINT java $JAVA_OPTS -XX:+UnlockExperimentalVMOptions -cp ${H2_DATA}/h2.jar org.h2.tools.Server -web -webAllowOthers -webPort ${H2_WEBPORT} -tcp -tcpAllowOthers -tcpPort ${H2_TCPPORT} -ifNotExists -baseDir ${H2_DATA}