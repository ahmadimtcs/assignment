package com.mahesh.weather.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import com.mahesh.weather.client.OpenWeatherRestClient;
import com.mahesh.weather.model.City;
import com.mahesh.weather.model.UserCityDetails;
import com.mahesh.weather.model.WeatherDetails;
import com.mahesh.weather.router.WeatherRouter;
import com.mahesh.weather.service.CityService;
import com.mahesh.weather.service.UserCityDetailsService;
import com.mahesh.weather.service.WeatherDetailsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@WebFluxTest
@ContextConfiguration(classes = {WeatherRouter.class, UserCityHandler.class,
    OpenWeatherRestClient.class, WeatherHandler.class
})
@AutoConfigureWebTestClient
class WeatherHandlerTest {

  static String WEATHER_ENDPOINT = "/v1/weatherCache";
  PodamFactory factory = new PodamFactoryImpl();

  @MockBean
  WeatherDetailsService weatherDetailsService;

  @MockBean
  UserCityDetailsService userCityDetailsService;

  @MockBean
  OpenWeatherRestClient openWeatherRestClient;

  @MockBean
  CityService cityService;

  @Autowired
  WebTestClient webTestClient;

  City city;

  UserCityDetails userCityDetails;

  WeatherDetails weatherDetails;

  @BeforeEach
  void setUp() {
    weatherDetails = new WeatherDetails();
    userCityDetails = new UserCityDetails();
    factory.populatePojo(weatherDetails);
    factory.populatePojo(userCityDetails);
    city = new City("1", "Pune", "18.521428", "73.8544541", "IN");
    userCityDetails.getCities().add(city);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  @WithMockUser(username = "user")
  void getWeatherOfAllUserCitiesWithCache() {

    when(userCityDetailsService.findUserCityDetails(isA(String.class)))
        .thenReturn(Mono.just(userCityDetails));

    when(weatherDetailsService.findByCityNameAndCountryWithCache(isA(City.class)))
        .thenReturn(Mono.just(weatherDetails));

    webTestClient.mutateWith(csrf())
        .get()
        .uri(WEATHER_ENDPOINT)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(WeatherDetails.class)
        .consumeWith(weatherDetailsEntityExchangeResult -> {
          var wd = weatherDetailsEntityExchangeResult.getResponseBody();
          assertNotNull(wd);
          assertTrue(wd.size() > 0);
        });
  }

  @Test
  @WithMockUser(username = "user")
  void getWeatherOfAllUserCitiesWithCache_NotFound() {

    when(userCityDetailsService.findUserCityDetails(isA(String.class)))
        .thenReturn(Mono.empty());

    webTestClient.mutateWith(csrf())
        .post()
        .uri(WEATHER_ENDPOINT + "/city")
        .bodyValue(city)
        .exchange()
        .expectStatus()
        .is5xxServerError();
  }

  @Test
  @WithMockUser(username = "user")
  void getWeatherForCityWithCache() {

    when(userCityDetailsService.findUserCityDetails(isA(String.class)))
        .thenReturn(Mono.just(userCityDetails));

    when(weatherDetailsService.findByCityNameAndCountryWithCache(isA(City.class)))
        .thenReturn(Mono.just(weatherDetails));

    webTestClient.mutateWith(csrf())
        .post()
        .uri(WEATHER_ENDPOINT + "/city")
        .bodyValue(city)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(WeatherDetails.class)
        .consumeWith(weatherDetailsEntityExchangeResult -> {
          var wd = weatherDetailsEntityExchangeResult.getResponseBody();
          assertNotNull(wd);
          assertEquals(weatherDetails.getWeatherId(), wd.getWeatherId());
          assertEquals(weatherDetails.getCity().getName(), wd.getCity().getName());
          assertEquals(weatherDetails.getCity().getCountry(), wd.getCity().getCountry());
        });
  }

  @Test
  @WithMockUser(username = "user")
  void getWeatherForCityWithCache_NotFound() {

    when(userCityDetailsService.findUserCityDetails(isA(String.class)))
        .thenReturn(Mono.empty());

    webTestClient.mutateWith(csrf())
        .post()
        .uri(WEATHER_ENDPOINT + "/city")
        .bodyValue(city)
        .exchange()
        .expectStatus()
        .is5xxServerError();
  }
}