package com.weather.assignment.repository;

import com.weather.assignment.document.WeatherDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WeatherRepository extends ReactiveMongoRepository<WeatherDetails, Long> {
}
