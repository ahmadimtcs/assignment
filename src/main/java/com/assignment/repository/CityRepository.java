package com.assignment.repository;

import com.assignment.entity.City;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CityRepository extends ReactiveMongoRepository<City, Long> {

    Mono<City> findByName(String city);
}

