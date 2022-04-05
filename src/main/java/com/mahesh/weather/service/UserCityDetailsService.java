package com.mahesh.weather.service;

import com.mahesh.weather.model.UserCityDetails;
import reactor.core.publisher.Mono;

public interface UserCityDetailsService {

  Mono<UserCityDetails> findOrCreateUserCityDetails(String userId);

  Mono<UserCityDetails> findUserCityDetails(String userId);

  Mono<UserCityDetails> save(UserCityDetails userCityDetails);

  Mono<Void> removeUserCityDetails(UserCityDetails userCityDetails);

  Mono<Void> removeById(String userId);

  Mono<UserCityDetails> update(UserCityDetails userCityDetails);
}
