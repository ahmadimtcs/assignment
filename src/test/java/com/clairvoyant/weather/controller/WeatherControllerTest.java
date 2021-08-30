package com.clairvoyant.weather.controller;

import static com.clairvoyant.weather.contants.WeatherConstants.WEATHER_END_POINT;

import com.clairvoyant.weather.document.WeatherDetails;
import com.clairvoyant.weather.exception.NotFoundException;
import com.clairvoyant.weather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 19-08-2021 13:22
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
class WeatherControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private WeatherRepository weatherRepository;


  @Test
  @WithMockUser(roles = "ADMIN")
  void getAllWeatherDetails() {
    WeatherDetails weatherDetails = this.setupData();
    Mockito.when(weatherRepository.findAll()).thenReturn(Flux.just(weatherDetails));
    webTestClient.get().uri(WEATHER_END_POINT).exchange().expectStatus().isOk()
        .expectBody()
        .jsonPath("[0].name").isEqualTo(this.setupData().getName());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void createWeatherDetails() {
    WeatherDetails weatherDetails = this.setupData();

    Mockito.when(weatherRepository.save(weatherDetails)).thenReturn(Mono.just(weatherDetails));
    webTestClient.post().uri(WEATHER_END_POINT).bodyValue(weatherDetails).exchange()
        .expectStatus()
        .isOk().expectBody(WeatherDetails.class);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateWeather() {
    WeatherDetails weatherDetails = this.setupData();
    Mockito.when(weatherRepository.findById(this.setupData().getId()))
        .thenReturn(Mono.just(weatherDetails));
    Mockito.when(weatherRepository.save(weatherDetails)).thenReturn(Mono.just(weatherDetails));
    webTestClient.put().uri(WEATHER_END_POINT.concat("/{id}"), this.setupData().getId())
        .bodyValue(weatherDetails)
        .exchange().expectStatus().isOk().expectBody(WeatherDetails.class);

  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getWeatherDetailsByCity() {
    WeatherDetails weatherDetails = this.setupData();
    Mockito.when(weatherRepository.findByName(this.setupData().getName()))
        .thenReturn(Mono.just(weatherDetails));
    webTestClient.get()
        .uri(WEATHER_END_POINT.concat("/city/{city}"), this.setupData().getName())
        .exchange().expectStatus()
        .isOk().expectBody().jsonPath("$.name").isEqualTo(this.setupData().getName());

  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteWeatherDetails() {
    Mockito.when(weatherRepository.deleteById(this.setupData().getId())).thenReturn(Mono.empty());
    webTestClient.delete()
        .uri(WEATHER_END_POINT.concat("/{id}"), this.setupData().getId())
        .accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody(Void.class);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getCityNotFoundException() {
    Mockito.when(weatherRepository.findByName(this.setupData().getName()))
        .thenReturn(Mono.error(new NotFoundException("NotFoundException error message!")));
    webTestClient.get().uri(WEATHER_END_POINT.concat("/city/{city}"), this.setupData().getName())
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  WeatherDetails setupData() {
    WeatherDetails weatherDetails = new WeatherDetails();
    weatherDetails.setId(123L);
    weatherDetails.setName("Mumbai");
    weatherDetails.setTemp(299.12);
    weatherDetails.setFeelsLike(300.12);
    return weatherDetails;

  }
}