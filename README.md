# Market Maven
This project demonstrates the implementation of Spring Boot 3.0. It includes the following features:

## Features
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security
* Customized access denied handling
* Logout mechanism
* Refresh token
* Transaction Processing

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
 
## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+


-> The application will be available at http://localhost:8080.

To install backend on your machine for the front-end integration:

1. Install docker on your machine and login on dockerhub
2. Run the following commands on your machine:

docker pull akatsande/txn-smc-core:latest

docker run -d -p 8080:8080 akatsande/txn-smc-core:latest

open http://localhost:8080/swagger-ui.html

For authentication:

      ADMIN - username: admin@mail.com
      -password: 1111
      USER - username: user@mail.com
      -password: 1111
