package com.clairvoyant.weather;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
public class WeatherCity {

  public static void main(String[] args) {

    SpringApplication.run(WeatherCity.class, args);
    log.info("done...");

  }

}
