package com.learnreactivespring.repository;

import com.learnreactivespring.model.City;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CityRepository extends ReactiveMongoRepository<City, String> {

}
