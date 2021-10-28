FROM java:8-jdk-alpine
COPY ./target/email-service-0.0.2-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch email-service-0.0.2-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","email-service-0.0.2-SNAPSHOT.jar"]