FROM openjdk:17-jdk-slim

# Copy project to the image
COPY target/luminousonion-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the front-end and backend ports
EXPOSE 8080 8081

CMD ["java", "-jar", "/app/app.jar"]