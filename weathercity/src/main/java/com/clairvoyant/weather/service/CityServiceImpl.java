package com.clairvoyant.weather.service;

import com.clairvoyant.weather.model.City;
import com.clairvoyant.weather.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityServiceImpl implements CityService {


  private final CityRepository repository;

  public CityServiceImpl(CityRepository repository) {
    this.repository = repository;
  }

  public Flux<City> getAllCity() {
    return repository.findAll();
  }

  public Mono<City> getCityByName(String name) {
    return repository.findByName(name);
  }

  public Mono<?> updateCity(City city) {
    return repository.save(city);
  }

  public Mono<Void> deleteCityById(Long id) {
    return repository.deleteById(id);
  }

}
