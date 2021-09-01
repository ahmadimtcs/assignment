package com.learnreactivespring.repository;

import com.learnreactivespring.model.Weather;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WeatherRepository extends ReactiveMongoRepository<Weather, String> {


  @Tailable
  Flux<Weather> findWeatherBy();

}
