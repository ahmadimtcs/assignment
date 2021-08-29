package com.weather.assignment.initializer;

import com.weather.assignment.service.WeatherService;
import org.springframework.boot.CommandLineRunner;

public class WeatherDataInitializer implements CommandLineRunner {

  private final WeatherService weatherService;

  public WeatherDataInitializer(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @Override
  public void run(String... args) throws Exception {
    weatherService.getWeatherDetails();
  }
}
