package com.mahesh.weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import com.mahesh.weather.model.UserCityDetails;
import com.mahesh.weather.repository.UserCityDetailsReactiveRepository;
import com.mahesh.weather.service.impl.UserCityDetailsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@WebFluxTest
class UserCityDetailsServiceTest {

  UserCityDetails userCity;

  PodamFactory factory = new PodamFactoryImpl();

  @Mock
  UserCityDetailsReactiveRepository userCityDetailsReactiveRepositoryMock;

  @InjectMocks
  UserCityDetailsServiceImpl userCityDetailsService;

  @BeforeEach
  void setUp() {
    userCity = new UserCityDetails();
    factory.populatePojo(userCity);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void findOrCreateUserCityDetails_CreateNew() {
    when(userCityDetailsReactiveRepositoryMock.findByUserId(isA(String.class)))
        .thenReturn(Mono.empty());

    when(userCityDetailsReactiveRepositoryMock.save(isA(UserCityDetails.class)))
        .thenReturn(Mono.just(userCity));

    var userCityDetailsMono = userCityDetailsService.findOrCreateUserCityDetails("abc").log();
    StepVerifier.create(userCityDetailsMono)
        .consumeNextWith(userCityDetails -> {
          assertEquals(userCity.getId(), userCityDetails.getId());
        })
        .verifyComplete();
  }

  @Test
  void findOrCreateUserCityDetails() {
    when(userCityDetailsReactiveRepositoryMock.findByUserId(isA(String.class)))
        .thenReturn(Mono.just(userCity));

    when(userCityDetailsReactiveRepositoryMock.save(isA(UserCityDetails.class)))
        .thenReturn(Mono.empty());

    var userCityDetailsMono = userCityDetailsService.findOrCreateUserCityDetails("abc").log();
    StepVerifier.create(userCityDetailsMono)
        .consumeNextWith(userCityDetails -> {
          assertEquals(userCity.getId(), userCityDetails.getId());
        })
        .verifyComplete();
  }

  @Test
  void findUserCityDetails() {

    when(userCityDetailsReactiveRepositoryMock.findByUserId(isA(String.class)))
        .thenReturn(Mono.just(userCity));

    var userCityDetailsMono = userCityDetailsService.findUserCityDetails("ucd");

    StepVerifier.create(userCityDetailsMono)
        .consumeNextWith(userCityDetails -> {
          assertEquals(userCity.getId(), userCityDetails.getId());
        })
        .verifyComplete();

  }

  @Test
  void save() {
    UserCityDetails ucd = new UserCityDetails();
    factory.populatePojo(ucd);
    ucd.setId(null);

    when(userCityDetailsReactiveRepositoryMock.save(isA(UserCityDetails.class)))
        .thenReturn(Mono.just(userCity));

    var userCityDetailsMono = userCityDetailsService.save(ucd);

    StepVerifier.create(userCityDetailsMono)
        .consumeNextWith(userCityDetails -> {
          assertEquals(userCity.getId(), userCityDetails.getId());
        })
        .verifyComplete();
  }

  @Test
  void removeUserCityDetails() {
    when(userCityDetailsReactiveRepositoryMock.delete(isA(UserCityDetails.class)))
        .thenReturn(Mono.empty());

    var userCityDetailsMono = userCityDetailsService.removeUserCityDetails(userCity);

    StepVerifier.create(userCityDetailsMono)
        .verifyComplete();
  }

  @Test
  void removeById() {
    when(userCityDetailsReactiveRepositoryMock.deleteById(isA(String.class)))
        .thenReturn(Mono.empty());

    var userCityDetailsMono = userCityDetailsService.removeById("ucd");

    StepVerifier.create(userCityDetailsMono)
        .verifyComplete();
  }
}