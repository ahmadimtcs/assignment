package com.mahesh.weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import com.mahesh.weather.client.OpenWeatherRestClient;
import com.mahesh.weather.model.City;
import com.mahesh.weather.repository.CityReactiveRepository;
import com.mahesh.weather.service.impl.CityServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest
class CityServiceTest {

  @Mock
  private CityReactiveRepository cityReactiveRepository;

  @InjectMocks
  private CityServiceImpl cityService;

  @Mock
  private OpenWeatherRestClient openWeatherRestClient;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void findByNameAndCountry() {
    City city = new City("1", "Pune", "18.521428", "73.8544541", "IN");

    when(cityReactiveRepository.findByNameAndCountry(isA(String.class), isA(String.class)))
        .thenReturn(Mono.just(city));

    var cityMongo = cityService.findByNameAndCountry("Pune", "IN").log();

    StepVerifier.create(cityMongo)
        .consumeNextWith(city1 -> {
          assertNotNull(city1.getId());
          assertEquals("1", city1.getId());
          assertEquals("Pune", city1.getName());
          assertEquals("IN", city1.getCountry());
        })
        .verifyComplete();
  }

  @Test
  void save() {
    City city = new City(null, "Pune", "18.521428", "73.8544541", "IN");
    City city1 = new City("1", "Pune", "18.521428", "73.8544541", "IN");

    when(cityReactiveRepository.save(any(City.class))).thenReturn(Mono.just(city1));
    var cityMongo = cityService.save(city).log();

    StepVerifier.create(cityMongo)
        .consumeNextWith(city2 -> {
          assertNotNull(city2.getId());
          assertEquals("1", city2.getId());
        })
        .verifyComplete();

  }

  @Test
  void checkCityInOpenWeather() {
    City city = new City("1", "Pune", "18.521428", "73.8544541", "IN");

    when(cityReactiveRepository.save(any(City.class))).thenReturn(Mono.just(city));
    when(openWeatherRestClient.findCityByName(isA(City.class)))
        .thenReturn(Mono.just(city));

    var cityMongo = cityService.checkCityInOpenWeather(city);
    StepVerifier.create(cityMongo)
        .consumeNextWith(city1 -> {
          assertNotNull(city1.getId());
          assertEquals("1", city1.getId());
        })
        .verifyComplete();
  }

  @Test
  void checkCityInOpenWeather_Empty() {
    City city = new City("1", "Pune", "18.521428", "73.8544541", "IN");

    //when(cityReactiveRepository.save(any(City.class))).thenReturn(Mono.just(city));
    when(openWeatherRestClient.findCityByName(isA(City.class)))
        .thenReturn(Mono.empty());

    var cityMongo = cityService.checkCityInOpenWeather(city);
    StepVerifier.create(cityMongo)
        .verifyComplete();
  }

  @Test
  void findCityByNameInOpenWeather() {
    City city = new City("1", "Pune", "18.521428", "73.8544541", "IN");

    when(openWeatherRestClient.findCityByName(isA(City.class)))
        .thenReturn(Mono.just(city));

    var cityMongo = cityService.findCityByNameInOpenWeather(city);
    StepVerifier.create(cityMongo)
        .consumeNextWith(city1 -> {
          assertNotNull(city1.getId());
          assertEquals("1", city1.getId());
        })
        .verifyComplete();
  }
}