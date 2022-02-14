FROM openjdk:8
ADD target/electric-tracking.jar electric-tracking.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "electric-tracking.jar"]
