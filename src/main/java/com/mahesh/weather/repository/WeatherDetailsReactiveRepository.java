package com.mahesh.weather.repository;

import com.mahesh.weather.model.WeatherDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface WeatherDetailsReactiveRepository extends
    ReactiveMongoRepository<WeatherDetails, String> {

  Mono<WeatherDetails> findByCityNameAndCityCountry(String cityName, String country);


  Mono<WeatherDetails> findByCoordinateLatAndCoordinateLon(String lat, String lon);
}