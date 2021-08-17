# Assignment

> Please fork this repository and create a pull request for review.

## Introduction
We need to track the current weather details about the city we are currently living in.

## Problem Statement 
Please write a program that will call [OpenWeather API](https://openweathermap.org/) and display the current weather. For application performance (caching) reasons we need to avoid multiple calls to the OpenWeather API. Store the data for N min locally and make the call only when the time has expired and refresh the local data.

The local data can be deleted at users' requests.

### Evaluation Criteria
* Only logged-in users should be able to get the weather details.
* Accept list of cities to be monitored.
* Use a simple noSQL DB for storing city data. Expose CRUD operation for the same.
* Expose API to see weather for specific city
* Design and implement weather-data cache accordingly 
* Use Reactive Spring.
* Follow the best practices for logging, exception handling.
* Integrate swagger in your application and follow the REST standard.
* Code coverage and branch ratio coverage should be more than 90%(the more the merrier).
* Use Java 8 features (Functional constructs like Lambda, Stream etc.) as much as you can.
* The code should be properly formatted. Please use Google code formatting for Java (more details [here](https://github.com/HPI-Information-Systems/Metanome/wiki/Installing-the-google-styleguide-settings-in-intellij-and-eclipse)).
* Use Gradle as build tool

## Build and Deployment
* Dockerize the application and run it

### Brownie Point
Run the application in [Minikube](https://minikube.sigs.k8s.io/docs/start/) or[MicroK8s](https://microk8s.io/)
