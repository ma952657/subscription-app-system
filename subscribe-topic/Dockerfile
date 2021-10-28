FROM java:8-jdk-alpine
COPY ./target/subscribe-topic-0.0.2-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch subscribe-topic-0.0.2-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","subscribe-topic-0.0.2-SNAPSHOT.jar"]