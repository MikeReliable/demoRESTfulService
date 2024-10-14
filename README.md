# DemoRESTfulService web application with implementation of access via JSON Web Token (JWT)

This project presents a RESTful service using Spring Boot 3.  
This is a TASK MANAGER that allows creating,
editing, viewing and deleting tasks. Each task contains a title, description, status ("waiting", "in progress", "
completed") and priority ("high", "medium", "low"), as well as the task author and executor. Users can manage their
tasks: create new ones, edit existing ones, view and delete, change the status and assign task executors. Task executors
can change the status of their tasks. You can leave comments on tasks. The API allows you to get tasks of a specific
author or executor, as well as all comments on them.  
It includes the following features:

## Features

* User authentication and authorization with JWT
* Password encryption with BCrypt
* Authorization with Spring Security
* Customized access denied handling
* Logout mechanism
* Refresh token
* Filtering and pagination of output data
* Incoming data validation
* Data caching
* API documentation Swagger UI
* Migrating data on first load with Liquibase
* Integration and unit tests
* Deploying service based on a Docker image

## Technologies

* Java 17
* Spring Boot 3
* Spring Security
* Spring JPA
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* PostgreSQL
* Docker

## Getting Started

To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
* Docker

To build and run the project, follow these steps:

* Clone the repository:

```sh
git clone https://github.com/MikeReliable/demoRESTfulService.git
 ```

* Navigate to the project directory: cd demoRESTfulService
* Build the project:

```sh
mvn clean install
 ```

* Build the docker image:

```sh
docker build -t demorestfulservice .
 ```

* Download PosrgreSQL docker image:

```sh
docker pull postgres:16
 ```

* Run the docker-compose file:

```sh
docker-compose up
 ```

-> The application will be available at http://localhost:8080  
-> Swagger UI specification will be available at http://localhost:8080/swagger-ui/index.html
