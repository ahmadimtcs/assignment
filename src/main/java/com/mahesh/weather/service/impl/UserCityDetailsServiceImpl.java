package com.mahesh.weather.service.impl;

import com.mahesh.weather.model.UserCityDetails;
import com.mahesh.weather.repository.UserCityDetailsReactiveRepository;
import com.mahesh.weather.service.UserCityDetailsService;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserCityDetailsServiceImpl implements UserCityDetailsService {

  private final UserCityDetailsReactiveRepository userCityDetailsReactiveRepository;

  UserCityDetailsServiceImpl(UserCityDetailsReactiveRepository userCityDetailsReactiveRepository) {
    this.userCityDetailsReactiveRepository = userCityDetailsReactiveRepository;
  }

  @Override
  public Mono<UserCityDetails> findOrCreateUserCityDetails(String userId) {
    return userCityDetailsReactiveRepository.findByUserId(userId)
        .switchIfEmpty(Mono.defer(() -> this.addUserCityDetails(userId)))
        .log();
  }

  @Override
  public Mono<UserCityDetails> findUserCityDetails(String userId) {
    return userCityDetailsReactiveRepository.findByUserId(userId)
        .log();
  }

  @Override
  public Mono<UserCityDetails> save(UserCityDetails userCityDetails) {
    return userCityDetailsReactiveRepository.save(userCityDetails);
  }

  @Override
  public Mono<UserCityDetails> update(UserCityDetails userCityDetails) {
    return userCityDetailsReactiveRepository.findByUserId(userCityDetails.getUserId())
        .flatMap(ucd -> {
          log.info("Cities size before update in update Method {}", ucd.getCities().size());
          ucd.getCities().addAll(userCityDetails.getCities());
          log.info("Cities size after update in update Method {}", ucd.getCities().size());
          return userCityDetailsReactiveRepository.save(ucd);
        })
        .switchIfEmpty(Mono.defer(() -> Mono.empty()));
  }

  @Override
  public Mono<Void> removeUserCityDetails(UserCityDetails userCityDetails) {
    return userCityDetailsReactiveRepository.delete(userCityDetails).log();
  }

  @Override
  public Mono<Void> removeById(String id) {
    return userCityDetailsReactiveRepository.deleteById(id);
  }

  private Mono<UserCityDetails> addUserCityDetails(String userId) {
    UserCityDetails userCityDetails = UserCityDetails.builder()
        .userId(userId)
        .cities(new HashSet<>())
        .build();
    log.info("Saving UserCityDetails");
    return this.save(userCityDetails);
  }
}
