## Environment
- Java version: 17
- Maven version: 3.*
- Spring Boot version: 3.0.6

## Data
Example of a Event data JSON object:
```json
{
    "id":1,
    "name": "Soft Summit",
    "location": "Xton City",
    "duration": 8,
    "cost": 30
}
```

## Requirements
Here you are provided a events database where you have to implement `/event` REST endpoint for following 3 operations.

`GET` request to `/event/byId/{id}`:
* return the event entry with given id and status code 200
* if the requested event entry doesn't exist, then status code 404 should be returned

`GET` request to `/event/top3?by={by}`:
* return the top 3 event entries sorted by given field and status code 200.
* for example: `/event/top3?by=cost` gives top 3 entries by cost
* if give `by` is invalid attribute(other than cost & duration), return status code 400

`GET` request to `/event/total?by={by}`:
* return the total value summed by given field and status code 200
* for example: `/event/total?by=duration` gives total duration of all events
* if give `by` is invalid attribute(other than cost & duration), return status code 400
 
`Test writing`
In addition to implementing the REST endpoints, you are supposed to write several(at least 3) unit tests to test your implementation.


## Commands
- run: 
```bash
mvn clean spring-boot:run
```
- install: 
```bash
mvn clean install
```
- test: 
```bash
mvn clean test
```