# Awesome postcode system

## Project description
Simple test system for managing AU postcodes.<br>
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


## Build and test
gradle build -x bootBuildImage

## Build, test app and build images
gradle build

## Start app
docker-compose up -d --build

## Stop app
docker-compose down

## Swagger
{host:port}/swagger-ui.html