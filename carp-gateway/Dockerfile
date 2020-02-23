FROM java:8
VOLUME /tmp
ADD target/carp-gateway-1.0-SNAPSHOT.jar carp-gateway.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","carp-gateway.jar"]