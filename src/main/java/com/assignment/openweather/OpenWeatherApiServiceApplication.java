package com.assignment.openweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableCaching
@EnableReactiveMongoRepositories
public class OpenWeatherApiServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OpenWeatherApiServiceApplication.class, args);
  }

}
