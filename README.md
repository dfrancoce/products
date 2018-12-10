# Products

This project contains a REST API to place orders in the system allowing the API consumer to add/remove products to each order.

### Prerequisites

Docker is required in order to start the app.

### Starting the app

To run the app just execute the following command in the root directory 
```
docker-compose up
```
Once everything is up and running, an UI with the endpoints provided by the API can be seen at 
```
localhost:8080/swagger-ui.html
```

### Testing the app

There is a JSON under the /tests folder that can be imported in Postman to execute a suit of tests against the API.
