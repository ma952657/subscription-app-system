FROM java:8-jdk-alpine
COPY ./target/public-service-0.0.2-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch public-service-0.0.2-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","public-service-0.0.2-SNAPSHOT.jar"]