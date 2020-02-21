FROM java:8
VOLUME /tmp
ADD target/carp-auth-start-1.0-SNAPSHOT.jar carp-auth.jar
EXPOSE 8001
ENTRYPOINT ["java","-jar","carp-auth.jar"]