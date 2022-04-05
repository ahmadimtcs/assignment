package com.mahesh.weather.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mahesh.weather.model.City;
import com.mahesh.weather.model.UserCityDetails;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@ActiveProfiles("test")
@SpringBootTest
class UserCityDetailsReactiveRepositoryIntgTest {

  @Autowired
  UserCityDetailsReactiveRepository userCityDetailsReactiveRepository;

  @Autowired
  CityReactiveRepository cityReactiveRepository;

  @BeforeEach
  void setUp() {
    var citySet = Set.of(new City("1", "Pune", "18.521428", "73.8544541", "IN"));
    var userCity = new UserCityDetails("11", "user", citySet);

    cityReactiveRepository.saveAll(citySet).blockLast();
    userCityDetailsReactiveRepository.save(userCity).block();
  }

  @AfterEach
  void tearDown() {
    cityReactiveRepository.deleteAll().block();
    userCityDetailsReactiveRepository.deleteAll().block();
  }

  @Test
  void findByUserId() {
    var userCityMono = userCityDetailsReactiveRepository.findByUserId("user").log();

    StepVerifier.create(userCityMono)
        .consumeNextWith(userCity -> {
          assertEquals("11", userCity.getId());
          assertEquals(1, userCity.getCities().size());
        })
        .verifyComplete();
  }

  @Test
  void save() {
    var citySet = Set.of(new City("1", "Pune", "18.521428", "73.8544541", "IN"));
    var userCity = new UserCityDetails(null, "user1", citySet);

    var userCityMono = userCityDetailsReactiveRepository.save(userCity);
    StepVerifier.create(userCityMono)
        .consumeNextWith(userCityDetails -> {
          assertNotNull(userCityDetails.getId());
          assertEquals("user1", userCityDetails.getUserId());
          assertEquals(1, userCityDetails.getCities().size());
        })
        .verifyComplete();
  }
}