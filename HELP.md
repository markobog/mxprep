# Mxprep project documentation

Mxprep is preparation spring boot REST API for MX project

### Technologies and tools

* spring boot 3.0
* java 17
* hibernate 6
* postgresql 15
* maven
* junit
* liquibase
* opencsv
* lombok
* swagger
* postman
* intellij community edition

### How to run application

* Check out project from github
  https://github.com/markobog/mxprep

* Run application with maven command
  mvn spring-boot:run

* Swagger link with endpoints
http://localhost:8080/swagger-ui/index.html

* Install postgresql from this link:
https://www.postgresql.org/download/

* Postgresql docker image:
https://hub.docker.com/_/postgres

Schema mxprep, tables, indexes and company table data will be created during application startup via liquibase

* Postgresql username/password:
  postgres/postgres

* List of endpoints

http://localhost:8080/stocks/get-highest-normalized-by-date/03-30-2023
http://localhost:8080/stocks/get-statistics-by-company/IBM
http://localhost:8080/stocks/get-normalized-descending
http://localhost:8080/stocks/get-statistics-by-company
http://localhost:8080/upload-csv-file

Valid company names are Apple, Microsoft, EPAM and IBM (app is case-sensitive for simplicity) 
Date format for endpoints is 03-30-2023

* Important

First import csv file/s with data via this endpoint:
http://localhost:8080/upload-csv-file
Using postman for this endpoint, select Body tab -> form-data -> in Key column add "file" and in Value column select one
of prepared test files in src/test/resources (file Download Data - STOCK_US_XNAS_AAPL.csv for instance) via file selector and
click Send to import data from csv file to database. After that you can call other endpoints.
  

