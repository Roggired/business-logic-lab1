docker-compose down
./gradlew build
cp build/libs/kickstoper-1-plain.war deployments/
docker-compose build
docker-compose up
