### Documentation
The db is not persistent. Each time the container ```simpleatm-bankdb-1``` is started, you also have to start the java app container ```simpleatm-bank-1```.

### Initializing the DB
The DB is automatically initialized in the Java code due to difficulties using a custom PostgreSQL Dockerfile to copy the <a href="init.sql">init.sql</a> file.
The ```simpleatm-bank-1``` server has to wait for the ```simpleatm-bankdb-1```
container to start, that's why we use the <a href="wait-for-it.sh">wait-for-it.sh</a> script to wait until there is a TCP connection from the DB.
