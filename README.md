# simple-user-profile

This project is simple user profile rest api with Vaadin client application.


## simple-user-profile-api
	GET /user-profiles - gets all user profiles
	GET /user-profiles/{id} - gets user profile by provided id
	POST /user-profiles, ContentType: application/json, Body: {"firstName":"John","lastName":"Dough"} - saves user profile
	DELETE /user-profiles/{id} - deletes user profile by provided id
	
	
## simple-user-profile-client

Built using Vaadin. Allows simple CRUD operation on user profile entity.


## build and run

In order to build application, please run `mvn clean install`, it will build application's jar files and create docker images.
In order to run application , please run `docker-compose up -d`. 