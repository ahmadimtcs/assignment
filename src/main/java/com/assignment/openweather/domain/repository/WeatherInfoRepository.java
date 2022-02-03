package com.assignment.openweather.domain.repository;

import com.assignment.openweather.domain.entity.WeatherEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface WeatherInfoRepository extends ReactiveMongoRepository<WeatherEntity, String> {

  Optional<Mono<WeatherEntity>> findByLocationName(String locationName);

}
