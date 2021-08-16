# Assignment

## Introduction
We need to track the current weather details about the city we are currently living in.

## Problem Statement 
Please write a program that will call [OpenWeather API](https://openweathermap.org/) and display the current weather. For application performance (caching) reasons we need to avoid the call to the OpenWeather API. Store the data for N min locally and make the call only when the time has expired and refresh the local data.

The local data can be deleted at users' requests.

### Evaluation Criteria
* Only logged-in users should be able to get the weather details.
* Use Reactive Spring.
* Logging, exception handling, returning proper HTTP error codes
* Integrate swagger in your application and follow the REST standard.
* Code coverage and branch ratio coverage should be more than 90%(the more the merrier).
* Use Java 8 features (Functional constructs like Lambda, Stream) as much as you can.
* The code should be properly formatted. Please use Google code formatting for Java (more details [here](https://github.com/HPI-Information-Systems/Metanome/wiki/Installing-the-google-styleguide-settings-in-intellij-and-eclipse)).
