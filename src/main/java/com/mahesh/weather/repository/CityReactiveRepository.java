package com.mahesh.weather.repository;

import com.mahesh.weather.model.City;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CityReactiveRepository extends ReactiveMongoRepository<City, String> {

  Mono<City> findByNameAndCountry(String name, String country);
}
