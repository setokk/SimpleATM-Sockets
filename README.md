### Simple ATM System using Sockets, PostgreSQL and docker-compose
This project is part of the Distributed Systems course, University of Macedonia (UoM).

### Prequisites
  - docker-compose

### Running the app
Build and run the containers using:
```shell
sudo docker-compose up -d
```
After the containers are created, run the client interactively:
```shell
sudo docker start -a -i simpleatm-client-1
```