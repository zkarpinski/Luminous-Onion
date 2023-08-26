FROM eclipse-temurin:17.0.8_7-jre-alpine
LABEL title="Lumionous Onion" \
    version="0.0.1" \
    author="Zachary Karpinski"\


RUN apk update && apk upgrade
# Copy project to the image
COPY target/luminousonion-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the front-end and backend ports
EXPOSE 8081

CMD ["java", "-jar", "/app/app.jar"]