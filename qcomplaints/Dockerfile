FROM quay.io/quarkus/quarkus-micro-image:1.0
WORKDIR /work/
COPY target/*-runner /work/sccstore-qcomplaints
RUN chmod 775 /work
EXPOSE 8080
CMD ["./sccstore-qcomplaints", "-Dquarkus.http.host=0.0.0.0"]