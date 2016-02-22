# GDVF - Query Token To SQL
This module is responsible for converting the tokens (came from a raw human query) into the SQL of underlying data source (postgresql, mysql, spark etc).

## Dependencies

- [Sparkjava](http://sparkjava.com) (well, do not confuse it with [Apache Spark](http://spark.apache.org/)) is used to build the api end points.
- Java `>=` 8
- Maven `>=` 3

## Api end-points

- Url: http://169.45.220.234:4567/querytosql ( Example endpoint; Deployed in [Koding VM](https://koding.com) )
- Method: POST
- Example:

```bash    
    afsar@afsar-ws-mac:querytoken-to-sql$ curl -i -H "Content-Type: application/json" -X POST --data '{"text": "what is tour cost"}' 'http://169.45.220.234:4567/querytosql'
    HTTP/1.1 200 OK
    Date: Mon, 22 Feb 2016 06:09:09 GMT
    Access-Control-Allow-Origin: *
    Access-Control-Allow-Methods: GET,PUT,POST,DELETE,OPTIONS
    Access-Control-Allow-Credentials: true
    Access-Control-Allow-Headers: Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin
    Content-Type: application/json
    Transfer-Encoding: chunked
    Server: Jetty(9.3.z-SNAPSHOT)
    
    {"sql":" SELECT tour.costPerPerson FROM tm.tour tour"}
```

# Running on server

```bash    
    mvn clean package
    java -jar target/query-to-sql-x.y.z.jar
```

