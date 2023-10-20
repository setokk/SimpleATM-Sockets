FROM openjdk:21-jdk-bullseye

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY pom.xml /app
COPY src /app/src

RUN mvn package

# Wait for db to start
COPY wait-for-it.sh ./wait-for-it.sh
RUN chmod +x ./wait-for-it.sh
ENTRYPOINT ["./wait-for-it.sh", "bankdb:5432", "--", "java", "-jar", "target/SimpleATM-1.0.jar"]