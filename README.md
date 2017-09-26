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

Once the server has started navigate to localhost:8080 and you should see the test client.
