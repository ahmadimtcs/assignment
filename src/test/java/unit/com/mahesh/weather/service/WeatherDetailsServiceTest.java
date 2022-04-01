package com.mahesh.weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import com.mahesh.weather.client.OpenWeatherRestClient;
import com.mahesh.weather.model.City;
import com.mahesh.weather.model.WeatherDetails;
import com.mahesh.weather.repository.WeatherDetailsReactiveRepository;
import com.mahesh.weather.service.impl.WeatherDetailsServiceImpl;
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
class WeatherDetailsServiceTest {

  PodamFactory factory = new PodamFactoryImpl();
  WeatherDetails weatherDetails;

  @InjectMocks
  WeatherDetailsServiceImpl weatherDetailsService = new WeatherDetailsServiceImpl("3");

  @Mock
  WeatherDetailsReactiveRepository weatherDetailsReactiveRepository;

  @Mock
  OpenWeatherRestClient openWeatherRestClient;

  @BeforeEach
  void setUp() {
    weatherDetails = new WeatherDetails();
    factory.populatePojo(weatherDetails);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void save() {
    when(weatherDetailsReactiveRepository.save(isA(WeatherDetails.class)))
        .thenReturn(Mono.just(weatherDetails));

    var wd = new WeatherDetails();
    factory.populatePojo(wd);

    var weatherDetailsMono = weatherDetailsService.save(wd).log();
    StepVerifier.create(weatherDetailsMono)
        .consumeNextWith(weatherInfo -> {
          assertNotNull(weatherInfo.getWeatherId());
          assertEquals(weatherDetails.getWeatherId(), weatherInfo.getWeatherId());
        })
        .verifyComplete();

  }

  @Test
  void findByCityNameAndCountry() {
    when(weatherDetailsReactiveRepository.findByCityNameAndCityCountry(isA(String.class),
        isA(String.class)))
        .thenReturn(Mono.just(weatherDetails));

    var weatherDetailsMono = weatherDetailsService.findByCityNameAndCountry("Pune", "IN").log();
    StepVerifier.create(weatherDetailsMono)
        .consumeNextWith(weatherInfo -> {
          assertNotNull(weatherInfo.getWeatherId());
          assertEquals(weatherDetails.getWeatherId(), weatherInfo.getWeatherId());
          assertEquals(weatherDetails, weatherInfo);
        })
        .verifyComplete();
  }

  @Test
  void findByLatAndLon() {
    when(weatherDetailsReactiveRepository.findByCoordinateLatAndCoordinateLon(isA(String.class),
        isA(String.class)))
        .thenReturn(Mono.just(weatherDetails));

    var weatherDetailsMono = weatherDetailsService.findByLatAndLon("78.10", "12.10").log();
    StepVerifier.create(weatherDetailsMono)
        .consumeNextWith(weatherInfo -> {
          assertNotNull(weatherInfo.getWeatherId());
          assertEquals(weatherDetails.getWeatherId(), weatherInfo.getWeatherId());
          assertEquals(weatherDetails, weatherInfo);
        })
        .verifyComplete();
  }

  @Test
  void getOpenWeatherForCity() {
    when(openWeatherRestClient.getWeatherOfCity(isA(City.class)))
        .thenReturn(Mono.just(weatherDetails));

    when(weatherDetailsReactiveRepository.save(isA(WeatherDetails.class)))
        .thenReturn(Mono.just(weatherDetails));

    City city = new City();
    factory.populatePojo(city);

    var weatherDetailsMono = weatherDetailsService.getOpenWeatherForCity(city);
    StepVerifier.create(weatherDetailsMono)
        .consumeNextWith(weatherInfo -> {
          assertNotNull(weatherInfo.getWeatherId());
          assertEquals(weatherDetails.getWeatherId(), weatherInfo.getWeatherId());
          assertEquals(weatherDetails, weatherInfo);
        })
        .verifyComplete();
  }

  @Test
  void update() {

    WeatherDetails wd = new WeatherDetails();
    factory.populatePojo(wd);

    when(weatherDetailsReactiveRepository.findById(isA(String.class)))
        .thenReturn(Mono.just(wd));

    when(weatherDetailsReactiveRepository.save(isA(WeatherDetails.class)))
        .thenReturn(Mono.just(weatherDetails));

    var weatherDetailsMono = weatherDetailsService.update(wd);
    StepVerifier.create(weatherDetailsMono)
        .consumeNextWith(weatherInfo -> {
          assertNotNull(weatherInfo.getWeatherId());
          assertEquals(weatherDetails.getWeatherId(), weatherInfo.getWeatherId());
          assertEquals(weatherDetails, weatherInfo);
        })
        .verifyComplete();

  }

  @Test
  void findByCityNameAndCountryWithCache() {
    when(openWeatherRestClient.getWeatherOfCity(isA(City.class)))
        .thenReturn(Mono.just(weatherDetails));

    when(weatherDetailsReactiveRepository.save(isA(WeatherDetails.class)))
        .thenReturn(Mono.just(weatherDetails));

    City city = new City();
    factory.populatePojo(city);

    var weatherDetailsMono = weatherDetailsService.findByCityNameAndCountryWithCache(city);
    StepVerifier.create(weatherDetailsMono)
        .consumeNextWith(weatherInfo -> {
          assertNotNull(weatherInfo.getWeatherId());
          assertEquals(weatherDetails.getWeatherId(), weatherInfo.getWeatherId());
          assertEquals(weatherDetails, weatherInfo);
        })
        .verifyComplete();

  }

}