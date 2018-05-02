# Backend Training Java

This project is related to the
[Scott Logic backend training](https://docs.google.com/a/scottlogic.co.uk/document/d/1HUDpO1fpfSFmYyDpPLJPcUJg4g8tYZeZLN7dN7KyDLA/edit?usp=sharing).

It contains a basic service skeleton with a simple client implementation (used for testing purposes) and a Spring Boot
server which also provides a Socket.IO server using [Netty-socketio](https://github.com/mrniko/netty-socketio).

## Getting started

To run the project checkout the code cd to the service-skeleton folder and run:

``` bash
gradle bootRun
```
Note: You might find it more convenient to run the project through your IDE and many have Gradle integration.

## Test Harness

Once the Spring Boot server has started navigate to localhost:8080 and you should see a test client which will help you get up and running with testing your websocket code.

If you want to make any changes to this the code for the harness is located in the [resources folder](service-skeleton/src/main/resources/static).
