package com.weather.assignment.initializer;

import com.weather.assignment.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class WeatherDataInitializer implements CommandLineRunner {

    @Autowired
    WeatherService weatherService;

    @Override
    public void run(String... args) throws Exception {

        weatherService.getWeather();

    }
}
