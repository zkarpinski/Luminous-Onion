FROM eclipse-temurin:17.0.8_7-jre-alpine

RUN apk update && apk upgrade

# Copy project to the image
COPY target/luminousonion-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the front-end and backend ports
EXPOSE 8080 8081 8082

CMD ["java", "-jar", "/app/app.jar"]