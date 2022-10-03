FROM docker.io/liquibase/liquibase:4.16.1

CMD ["sh", "-c", "docker-entrypoint.sh --url=${URL} --username=${USERNAME} --password=${PASSWORD} --classpath=/liquibase/changelog --changeLogFile=relative/changelog.xml update"]