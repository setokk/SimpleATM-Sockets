FROM openjdk:21-jdk-bullseye

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY pom.xml /app
COPY src /app/src

RUN mvn package

CMD ["java", "-jar", "target/SimpleATM-1.0.jar"]