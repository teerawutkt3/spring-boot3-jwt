mvn clean install -Dmaven.test.skip=true
docker build -t app-api:latest .
docker tag app-api:latest teerawutkt/sprint3-jwt:latest
docker push teerawutkt/sprint3-jwt:latest