# Exercise REST Service
This Spring Boot 2 Rest Application is a presentation about Rest Application with
specific Rest guidelines and integration with Cassandra.

### Endpoints
For all endpoints, user has to include X-TOKEN, which is the customer id 
as a mock security.

#### GET http://localhost:19350/rest/item (Get all items for customer)

#### GET http://localhost:19350/rest/item/{{itemId}} (Get specific item for customer)

#### POST http://localhost:19350/rest/item (Save a new item)
Example of body:
{
	"itemId":"1",
	"status":"A",
	"value":"20"
}

### Cassandra
#### Installation
In order to run cassandra, you can use Docker image:
* docker pull cassandra
* docker run --name cassandra -d cassandra
* docker inspect cassandra (copy IP Address and paste it in application.yml cassandra.contactpoints)

#### Schema deployment
* docker exec -it cassandra bash
* cqlsh
* Run schema.cql commands