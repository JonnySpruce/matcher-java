# back-end-training-java

## Java EE

### Starting server

mvn wildfly-swarm:run

or

mvn package  
java -jar back-end-training-swarm.jar

### Default port

http://localhost:8080/healthcheck

## Matching Engine

### Potential expansion paths

* Push notification on successful trade (MQ, WebSockets?)
* Authentication to auto assign account number to orders/listings.
* Authentication to limit viewable listings (own listings only?)
* External service call for currency conversion rates to support localisation.
* Persistence for DR (DB backed impl of OrderMatcher?)
* Auditing to collect account stats (number trades, total profit/loss, e.t.c.)
* Filters/interceptors (CORS?)
* Service layer validation (JSR303).
* Enhanced locking (container managed, e.t.c.)