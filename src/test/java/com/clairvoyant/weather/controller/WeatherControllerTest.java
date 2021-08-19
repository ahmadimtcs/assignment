package com.clairvoyant.weather.controller;

import static com.clairvoyant.weather.contants.WeatherConstants.WEATHER_END_POINT;

import com.clairvoyant.weather.document.WeatherDetails;
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
  WebTestClient webTestClient;

  @MockBean
  private WeatherRepository weatherRepository;

  @Test
  @WithMockUser(roles = "ADMIN")
  void getAllWeatherDetails() {
    Mockito.when(weatherRepository.findAll())
        .thenReturn(Flux.just(WeatherDetails
            .builder()
            .name("Mumbai")
            .build()
        ));
    webTestClient.get().uri(WEATHER_END_POINT)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("[0].name").isEqualTo("Mumbai");
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void createWeatherDetails() {
    WeatherDetails weatherDetails = WeatherDetails
        .builder()
        .id(null)
        .name("Mumbai")
        .temp(299.12)
        .feels_like(300.12)
        .build();

    Mockito.when(weatherRepository.save(weatherDetails))
        .thenReturn(Mono.just(weatherDetails));
    webTestClient.post().uri(WEATHER_END_POINT)
        .bodyValue(weatherDetails)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(WeatherDetails.class);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateWeather() {
    WeatherDetails weatherDetails = WeatherDetails
        .builder()
        .id(8131499L)
        .name("Mumbai")
        .temp(299.12)
        .feels_like(300.12)
        .build();
    Mockito.when(weatherRepository.findById(8131499L))
        .thenReturn(Mono.just(weatherDetails));
    Mockito.when(weatherRepository.save(weatherDetails))
        .thenReturn(Mono.just(weatherDetails));
    webTestClient.put().uri(WEATHER_END_POINT)
        .bodyValue(weatherDetails)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(WeatherDetails.class);

  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void getWeatherDetailsByCity() {
    Mockito.when(weatherRepository.findByName("Mumbai"))
        .thenReturn(Mono.just(WeatherDetails
            .builder()
            .name("Mumbai")
            .build()
        ));
    webTestClient.get().uri(WEATHER_END_POINT.concat("/city/{city}"), "Mumbai")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.name").isEqualTo("Mumbai");

  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteWeatherDetails() {
    Mockito.when(weatherRepository.deleteById(8131499L))
        .thenReturn(Mono.empty());
    webTestClient.delete()
        .uri(WEATHER_END_POINT.concat("/{id}"), 8131499L)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Void.class);
  }

}