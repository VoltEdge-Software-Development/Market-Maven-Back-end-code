To install backend on your machine

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
