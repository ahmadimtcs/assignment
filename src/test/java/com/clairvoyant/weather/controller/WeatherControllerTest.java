package com.clairvoyant.weather.controller;

import static com.clairvoyant.weather.contants.WeatherConstants.WEATHER_END_POINT;

import com.clairvoyant.weather.document.WeatherDetails;
import com.clairvoyant.weather.exception.NotFoundException;
import com.clairvoyant.weather.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 19-08-2021 13:22
 */

@SpringBootTest
@AutoConfigureWebTestClient
class WeatherControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private WeatherRepository weatherRepository;

  private static WeatherDetails weatherDetails;

  @BeforeAll
  public static void setup() {
    WeatherDetails weatherDetail = new WeatherDetails();
    weatherDetail.setId(123L);
    weatherDetail.setName("Mumbai");
    weatherDetail.setTemp(299.12);
    weatherDetail.setFeelsLike(300.12);
    weatherDetails = weatherDetail;

  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getAllWeatherDetails() {
    Mockito.when(weatherRepository.findAll()).thenReturn(Flux.just(weatherDetails));
    webTestClient.get().uri(WEATHER_END_POINT).exchange().expectStatus().isOk()
        .expectBody()
        .jsonPath("[0].name").isEqualTo(weatherDetails.getName());
  }


  @Test
  @WithMockUser(roles = "ADMIN")
  void createWeatherDetails() {
    Mockito.when(weatherRepository.save(weatherDetails)).thenReturn(Mono.just(weatherDetails));
    webTestClient.post().uri(WEATHER_END_POINT).bodyValue(weatherDetails).exchange()
        .expectStatus()
        .isOk().expectBody(WeatherDetails.class);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateWeather() {
    Mockito.when(weatherRepository.findById(weatherDetails.getId()))
        .thenReturn(Mono.just(weatherDetails));
    Mockito.when(weatherRepository.save(weatherDetails)).thenReturn(Mono.just(weatherDetails));
    webTestClient.put().uri(WEATHER_END_POINT.concat("/{id}"), weatherDetails.getId())
        .bodyValue(weatherDetails)
        .exchange().expectStatus().isOk().expectBody(WeatherDetails.class);
  }


  @Test
  @WithMockUser(roles = "ADMIN")
  void getWeatherDetailsByCity() {
    Mockito.when(weatherRepository.findByName(weatherDetails.getName()))
        .thenReturn(Mono.just(weatherDetails));
    webTestClient.get()
        .uri(WEATHER_END_POINT.concat("/city/{city}"), weatherDetails.getName())
        .exchange().expectStatus()
        .isOk().expectBody().jsonPath("$.name").isEqualTo(weatherDetails.getName());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteWeatherDetails() {
    Mockito.when(weatherRepository.deleteById(weatherDetails.getId())).thenReturn(Mono.empty());
    webTestClient.delete()
        .uri(WEATHER_END_POINT.concat("/{id}"), weatherDetails.getId())
        .accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody(Void.class);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getCityNotFoundException() {
    Mockito.when(weatherRepository.findByName(weatherDetails.getName()))
        .thenReturn(Mono.error(new NotFoundException("NotFoundException error message!")));
    webTestClient.get().uri(WEATHER_END_POINT.concat("/city/{city}"), weatherDetails.getName())
        .exchange()
        .expectStatus()
        .isNotFound();
  }


}