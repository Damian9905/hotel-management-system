FROM adoptopenjdk:11-jre-hotspot
ADD target/hotel-app.jar hotel-app.jar
ENTRYPOINT ["java", "-jar", "hotel-app.jar"]
EXPOSE 8080