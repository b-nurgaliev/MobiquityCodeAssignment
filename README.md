## MobiquityCodeAssignment
This repository is created to share automated test project for qa task.

THERE ARE FOUND BUGS

## Technologies:
* Java 8
* Junit 5.8
* Rest API
* Cucumber 7
* Allure 2.17

## Project requirements:
* Java Development Kit 8 and higher
* Maven
* Circle CI

## Setup project:
Clone as a Maven project

## Run tests:
Run `mvn clean test` in console

## Generating Allure report:
* Report can be generated only after test run!
* Run `mvn allure:serve` locally from command line to generate Allure report and it will be automatically opened.

## Circle CI
Project integrated with Circle CI, Allure report can be viewed on the "Artifact" tab - allure-report/index.html
Dashboard can be found https://app.circleci.com/pipelines/github/b-nurgaliev/MobiquityCodeAssignment

## Found bugs
### Bug #1: Incorrect response status code and response body when requesting non-existing user by user id
Priority: Medium
Steps to reproduce:
1. Send GET /users/?Id={userId} request
2. Response status code is 200
3. Response body contains all users

Actual Result: If there are no users found by id: we get response code = 200 and response body contains all users.
Expected Result: If there are no users found by id we should get 404 response code and empty response body.

### Bug #2: Incorrect response status code when requesting user by non-existing username

Priority: Minor
Steps to reproduce:
1. Send GET /users/?username={username} request
2. Response status code is 200

Actual Result: I got status code = 200 when did request with non-existing username.
Expected Result: If there are no users found by username we should get 404 response code and empty response body.