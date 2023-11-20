FROM cgr.dev/chainguard/jdk:latest
LABEL title="Lumionous Onion" \
    version="0.0.1" \
    author="Zachary Karpinski"

# Copy project to the image
COPY target/luminousonion-core-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the app ports
EXPOSE 8081

CMD ["java", "-jar", "/app/app.jar"]