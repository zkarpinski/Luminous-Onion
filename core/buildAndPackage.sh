# Build, Test & Package the springboot project
./mvnw clean package

# Created docker image from the Dockerfile
docker build -t "zkarpinski/luminous-onion:latest" .

# Create container from the image and run on port 80
docker run -d -P --name "luminousonion_test" "zkarpinski/luminous-onion:latest"