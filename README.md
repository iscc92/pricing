# ~~~ PRICING API ~~~
This project is a simples API to calculate cost prices for services billed to a specific Customer. The typical Use 
Case consists of giving as input CustomerId, StartDate and EndDate (all three in String format), and receiving a JSON 
object containing your billing price. 

The database is the primary source of customer configuration, this meaning, no endpoint exists to change 
pricing, free days offer or other parameters. All this changes must be done directly in the database. 

## Running the application

Before installing and building the application, please, run:
```shell script
docker-compose up
```
on the root of the project, in order to build the container for PostgresSQL database installation, and to populate the 
database (for scripts 
refer to ./sql/create_tables.sql).

Then you can install by running:
```shell script
mvn clean install
```

and, after you can run by entering:
```shell script
mvn spring-boot:run
```


## Documentation and Rest Interface Interaction

As to obtain OpenAPI documentation in JSON format, please visit http://localhost:8080/pricing-openapi. To interact with 
the endpoints (including actuator), please refer to http://localhost:8080/pricing-ui.html. In both cases, make sure the 
application is running. 

## Assumptions
It's assumed both dates are chronologically coherent and formatted, meaning starDate always occurs before endDate 
   and input format is the correct one YYYY-MM-dd (schema commitment between different systems is assumed).
   

## Future possible integrations
Sentry, Jenkins pipeline. 