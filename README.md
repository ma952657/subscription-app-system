# Subscription App #

This app is using the microservice architecture to handle following the newsletter subscriptions operations.
* Create new Subscription
* Cancel existing Subscription
* Get Details of a Subscription
* Get all Subscription
* Email Notification to the End User

## Build Instructions ##

Docker images, including service .jars and their dependencies can be built from the root directory using:

```bash
mvn install
```

To run the application locally:
```bash
docker-compose up
```

Each service can be built individually by running ```bash mvn install``` in the service root directory.

The available docker images after building:
* public-service {tagged version}
* subscribe-service {tagged version}
* subscribe-topic {tagged version}
* email-service {tagged version}

## Usage ##
* Below are the Endpoints for the following operations
1. Create a new Subscription
   `http://localhost:8080/subscribe/`
   MethodType:`POST`.
An example request body is:
```json
{
  "email" : "mahesh@gmail.com",
  "firstname" : "Mahesh",
  "gender" : "MALE",
  "date_of_birth" : "1986-12-14",
  "has_consent" : true,
  "newsletter_id" : "1234"
}
```
   Response - Autoincrement with Prefix SR like `SR1` corresponds to the persisted subscription id.

2.Get Details of a Subscription
  `http://localhost:8080/subscribe/{subscription_id}`
Method:`GET`
Response:
```json
{
  "subsId": "SR1",
  "email" : "mahesh@gmail.com",
  "firstname" : "Mahesh",
  "gender" : "MALE",
  "date_of_birth" : "1986-12-14",
  "has_consent" : true,
  "newsletter_id" : "1234"
}
```
3.Get All Subscriptions
`http://localhost:8080/subscribe/getAll`
Method:`GET`
Response:
```json
[
  {
  "subsId": "SR1",
  "email" : "mahesh@gmail.com",
  "firstname" : "Mahesh",
  "gender" : "MALE",
  "date_of_birth" : "1986-12-14",
  "has_consent" : true,
  "newsletter_id" : "1234"
  }
]
```
4.Cancel Existing Subscription
`http://localhost:8080/subscribe/{subscription_id}`
Method:`DELETE`

### Service Documentation ###

* public-service https://app.swaggerhub.com/apis/maheshwar6/public-service/1.0.1
* subscribe-service https://app.swaggerhub.com/apis/maheshwar6/subscription-service/1.0.1
* subscribe-topic https://app.swaggerhub.com/apis/maheshwar6/subscription-topic/1.0.0
* email-service - has no accessible end point, it listens for subscriptions to email from the ActiveMQ subscription topic

## Tech Choices ##

| Functional Area       | Technology            | Justification                                       |  
|-----------------------|-----------------------|-----------------------------------------------------|
| Services              | Java with Spring Boot | Specified by the client                             | 
| Dependency Management | Maven                 | Support for docker image builds                     |   
|                       |                       | Previously Used by the developer                    |  
| Container             | Docker                | Specified by the client                             | 
| Messaging             | ActiveMQ              | Supports DLQs to handle comms with low SLA services | 
|                       |                       | Has Spring library support                          | 
|                       |                       | Has public docker images to use with docker compose | 
|                       |                       | Can run an embedded instance for testing            | 
| Persistence           | MongoDB               | Simple document store, we only store one type       | 
|                       |                       | Has Spring library support                          | 
|                       |                       | Has public docker images to use with docker compose | 
|                       |                       | Can run an embedded instance for testing            | 


## Technical Decisions ##

OpenAPI is still used as the documentation specification for all micro services.

ActiveMQ and Mongo were chosen as they had embedded support for brokers which helps with unit testing.

ActiveMQ is lighter weight to configure than Kafka.

MongoDB is lightweight to configure and a flat indexed document data store gives faster access.


## Improvements:

There are some improvements that should be made here:

- **Testing**: Include contract testing for the services.
- **Security**: Implement the security like OAuth2 in code.
- **Error handling**: Errors are logged and error messages returned to clients however, we can also implement fallbacks, 'circuit breakers' and default return cases to make our app more resilient.
- And finally, we can always refactor and improve the code :)

