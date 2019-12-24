FROM maven:3-jdk-8 AS builder

WORKDIR /build
COPY . .

RUN mvn package

FROM openjdk:8-jre-slim

WORKDIR /app
COPY --from=builder /build/target/marylou-api-1.0-jar-with-dependencies.jar /app/marylou.jar

CMD java -jar marylou.jar