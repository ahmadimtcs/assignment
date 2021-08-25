package com.clairvoyant.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class WeatherCity {

	public static void main(String[] args) {

		SpringApplication.run(WeatherCity.class, args);
		System.out.println("Done....");

	}

}
