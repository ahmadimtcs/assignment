package com.clairvoyant.weather.service;

import com.clairvoyant.weather.model.City;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityService {

  public Flux<City> getAllCity();

  public Mono<City> getCityByName(String name);

  public Mono<?> updateCity(City city);

  public Mono<Void> deleteCityById(Long id);
}

