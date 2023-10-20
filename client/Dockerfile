FROM openjdk:21-jdk-bullseye

WORKDIR /app

COPY . /app

RUN find . -name "*.java" -exec javac {} \;

CMD ["java", "Client"]