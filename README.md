# Awesome postcode system

## Project description
Simple system for managing AU postcodes and suburbs.<br>
System has following features:
- add new postcode in conjunction with suburbs
- get a sorted suburb name list as well as the total number of characters of all names combined

## Tech stack
- java 17
- spring boot
- spring data
- gradle
- lombok
- postgres
- swagger
- docker(gradle plugin)

## Build and test without docker images building
gradle build -x bootBuildImage

## Build, test app and build docker images
gradle build

## Start only postgresql
docker-compose up -d --build

## Start app and postgresql
docker-compose --profile full  up -d --build

## Stop containers app and delete volumes
docker-compose down -v

## Testing idea:

- Swagger<br>
http://localhost:8080/swagger-ui/index.html

- Postman<br> 
Please use postcodes.postman_collection.json file from project root directory
