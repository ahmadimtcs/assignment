package com.mahesh.weather.service.impl;

import com.mahesh.weather.client.OpenWeatherRestClient;
import com.mahesh.weather.model.City;
import com.mahesh.weather.repository.CityReactiveRepository;
import com.mahesh.weather.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CityServiceImpl implements CityService {

  private CityReactiveRepository cityReactiveRepository;

  private OpenWeatherRestClient openWeatherRestClient;

  CityServiceImpl(CityReactiveRepository cityReactiveRepository,
      OpenWeatherRestClient openWeatherRestClient) {
    this.cityReactiveRepository = cityReactiveRepository;
    this.openWeatherRestClient = openWeatherRestClient;
  }

  @Override
  public Mono<City> findByNameAndCountry(String name, String country) {
    return cityReactiveRepository.findByNameAndCountry(name, country);
  }

  @Override
  public Mono<City> save(City cityDetails) {
    return cityReactiveRepository.save(cityDetails);
  }

  @Override
  public Mono<City> checkCityInOpenWeather(City city) {
    return openWeatherRestClient.findCityByName(city)
        .flatMap(this::save)
        .switchIfEmpty(Mono.defer(() -> Mono.empty()))
        .log();
  }

  @Override
  public Mono<City> findCityByNameInOpenWeather(City city) {
    return openWeatherRestClient.findCityByName(city).log();
  }
}
