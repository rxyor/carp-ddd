FROM java:8
VOLUME /tmp
ADD target/carp-ums-start-1.0-SNAPSHOT.jar carp-ums.jar
EXPOSE 8002
ENTRYPOINT ["java","-jar","carp-ums.jar"]