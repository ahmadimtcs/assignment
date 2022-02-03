package com.assignment.openweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OpenWeatherApiServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OpenWeatherApiServiceApplication.class, args);
  }

}
