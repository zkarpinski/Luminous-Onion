:: Build, Test & Package the springboot project
call mvnw.cmd clean package

:: Created docker image from the Dockerfile
docker build -t "luminousonion" .

:: Create container from the image and run on port 80
docker run -d -p 8080:8080 -p 8081:8081 --name "luminousonion_test" luminousonion

