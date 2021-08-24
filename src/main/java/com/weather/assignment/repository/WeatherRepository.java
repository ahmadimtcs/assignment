package com.weather.assignment.repository;

import com.weather.assignment.document.WeatherElements;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WeatherRepository extends ReactiveMongoRepository<WeatherElements, Long> {
}
