FROM java:8-jdk-alpine
COPY ./target/subscribe-service-0.0.2-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch subscribe-service-0.0.2-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","subscribe-service-0.0.2-SNAPSHOT.jar"]