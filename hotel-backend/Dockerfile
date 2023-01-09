FROM maven:3.5-jdk-11-slim AS builder
WORKDIR /app
COPY pom.xml ./
COPY src ./src

RUN mvn clean install

# Second stage: Minimal runtime environment
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app

# copy jar from the first stage
COPY --from=builder /app/target/*.jar hotel-app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hotel-app.jar"]