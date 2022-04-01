package com.mahesh.weather.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mahesh.weather.model.City;
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
class CityReactiveRepositoryIntgTest {

  @BeforeEach
  void setUp() {
    var citySet = Set.of(new City("1", "Pune", "18.521428", "73.8544541", "IN"));
    cityReactiveRepository.saveAll(citySet).blockLast();
  }

  @AfterEach
  void tearDown() {
    cityReactiveRepository.deleteAll().block();
  }

  @Test
  void findByNameAndCountry() {
    var cityMongo = cityReactiveRepository.findByNameAndCountry("Pune", "IN").log();
    StepVerifier.create(cityMongo)
        .consumeNextWith(city1 -> {
          assertEquals("1", city1.getId());
          assertEquals("Pune", city1.getName());
        })
        .verifyComplete();
  }

  @Autowired
  CityReactiveRepository cityReactiveRepository;

  @Test
  void save() {
    City city = new City(null, "Mumbai", "19.0759899", "72.8773928", "IN");

    var cityMongo = cityReactiveRepository.save(city).log();

    StepVerifier.create(cityMongo)
        .consumeNextWith(city1 -> {
          assertNotNull(city1.getId());
          assertEquals("Mumbai", city1.getName());
        })
        .verifyComplete();

  }
}