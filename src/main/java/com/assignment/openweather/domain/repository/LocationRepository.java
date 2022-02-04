package com.assignment.openweather.domain.repository;

import com.assignment.openweather.domain.entity.LocationEntity;

import java.util.Optional;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LocationRepository extends ReactiveMongoRepository<LocationEntity, String> {

  Optional<Mono<LocationEntity>> findByLocationName(String locationName);

}
