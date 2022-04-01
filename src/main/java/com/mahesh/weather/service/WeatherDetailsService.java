package com.mahesh.weather.service;

import com.mahesh.weather.model.City;
import com.mahesh.weather.model.WeatherDetails;
import reactor.core.publisher.Mono;

public interface WeatherDetailsService {

  Mono<WeatherDetails> save(WeatherDetails weatherDetails);

  Mono<WeatherDetails> findByCityNameAndCountry(String name, String country);

  Mono<WeatherDetails> findByLatAndLon(String lat, String lon);

  Mono<WeatherDetails> getOpenWeatherForCity(City city);

  Mono<WeatherDetails> update(WeatherDetails wd);

  Mono<WeatherDetails> findByCityNameAndCountryWithCache(City city);
}
