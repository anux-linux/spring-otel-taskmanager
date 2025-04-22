# Task-Management Applikation with Spring Boot

A simple task management web application based on Spring Boot and Thymeleaf.
This application allows you to create, edit, and delete tasks.

## Features

- **CRUD Operations**: create, edit, and delete tasks.
- **Web Interface**: HTML templates with Thymeleaf for browser display.
- **Container**: Application and database run in their own Docker container.
- **OpenTelemetry** Requests are traced with open telemetry and send to an Otel collector.

## Prerequisites

- Unix / Linux system
- Java JDK 17
- Maven 4
- Docker

## Run

1. Set username and password in config.
    - application.properties
   ```bash 
    spring.datasource.url=jdbc:postgresql://postgresql:5432/db_task-manager
    spring.datasource.username=postgres
    spring.datasource.password=root
    ```
    - docker-compose.yaml
    ```bash
    [...]
   
    SPRING_DATASOURCE_URL: jdbc:postgresql://postgresql:5432/db_task-manager
    SPRING_DATASOURCE_USERNAME: postgres
    SPRING_DATASOURCE_PASSWORD: postgres1
   
    [...]
   
    POSTGRES_PASSWORD: postgres1
    POSTGRES_USER: postgres
    POSTGRES_DB: db_task-manager
   
   [...]
    ```

2. Navigate to the project root where the ```pom.xml``` and ```docker-compose.yaml``` are located.
3. Build the java package:

```bash
mvn clean package
```

4. Build and run the docker images / container.

```bash
docker compose up --build
```

5. The application is exposed on port 8888.

## Available Configurations

- To send the Traces to a monitoring service
  like [Grafana](https://grafana.com/docs/grafana-cloud/send-data/otlp/send-data-otlp/)
  or [Honeycomb](https://docs.honeycomb.io/send-data/opentelemetry/collector/) it is possible to adapt the collector
  config or application settings.

## Unit Tests

To run unit tests run this command ```mvn clean test ```