package com.mahesh.weather.service;

import com.mahesh.weather.model.City;
import reactor.core.publisher.Mono;

public interface CityService {

  Mono<City> findByNameAndCountry(String name, String country);

  Mono<City> save(City cityDetails);

  Mono<City> checkCityInOpenWeather(City city);

  Mono<City> findCityByNameInOpenWeather(City city);
}
