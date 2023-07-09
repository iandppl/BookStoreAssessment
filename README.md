# Book Store API

Software Requirement:

&#8226; Spring boot version: 3.1.0 and above

&#8226; Java version: 17 and above

&#8226; Postgres version: 14 and above

&#8226; Docker

Installation:

&#8226; Open terminal and run these commands:
1) docker pull postgres:alpine (To download the docker image of postgres)
2) docker run --name postgres-spring -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres:alpine (run the postgres docker image locally)
3) docker exec -it postgres-spring bash (execute the image using bash command)
4) psql -U postgres (use super user to enter to postgres command prompt)
5) CREATE DATABASE bookstore (create the database that is required for this application to run)

&#8226; Run "mvn clean install" to install the application

&#8226; Run "mvn spring-boot:run" to run the application

APIs:

APIs are guarded with an api key which is in the authentication service
To add this key, add the key as X-API-KEY with the value as "BookStore".
This portion of the guarding of api can be replaced with JWT
1) Get "/api/book?titleOrAuthorName={titleOrAuthorName}"
   - Fetches all books that contains either the title or author (exact match)
2) Post "/api/book/" 
   - Creates BookDTO objects and takes in BookDTO object and if book id is created then BookCreationErrorException will be thrown and will return true if successful
   - Example request body input:{
        "isbn": "1236",
        "title": "Little Mermaid",
        "authorDTOList": [
           {
              "name":"John",
              "birthday":"11/11/1991"
           }
        ],
        "year":2012,
        "price": 20.50,
        "genre":"Fiction"
     }
3) Put "/api/book/"
   - Update BookDTO objects and takes in BookDTO object and if book id is created then BookUpdateErrorException will be thrown and will return true if successful
   - Example request body input:{
     "isbn": "1236",
     "title": "Little Mermaid",
     "authorDTOList": [
     {
     "name":"John",
     "birthday":"11/11/1991"
     }
     ],
     "year":2012,
     "price": 20.50,
     "genre":"Fiction"
     }
4) Delete "/api/book?isbn={isbn}" 
   - Delete books using isbn id to delete books in database
   - Will return true if delete is successful
   - Will throw BooksDeletionErrorException for deletion error
   - Only Owner role can trigger this api
   - Role can be added via the http request header with X-ROLE as the key and Owner as the value
   - This portion of the api guard can be replaced with JWT role authorisation
