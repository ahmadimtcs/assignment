package com.mahesh.weather.repository;

import com.mahesh.weather.model.UserCityDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserCityDetailsReactiveRepository extends
    ReactiveMongoRepository<UserCityDetails, String> {

  Mono<UserCityDetails> findByUserId(String userId);
}
