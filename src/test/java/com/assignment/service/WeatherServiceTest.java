package com.assignment.service;

import com.assignment.entity.City;
import com.assignment.handler.CityHandler;
import com.assignment.repository.CityRepository;
import com.assignment.routing.CityRouter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@AutoConfigureWebTestClient
@WebFluxTest
@ContextConfiguration(classes = {CityService.class, CityRouter.class, CityHandler.class})
@Slf4j
public class WeatherServiceTest {

  @Autowired private WebTestClient webTestClient;

  @MockBean private CityRepository cityRepository;

  private static City city;

  @BeforeAll
  public static void setup() throws IOException {
    city =
        new ObjectMapper()
            .readValue(new ClassPathResource("data/Weather.json").getFile(), City.class);
  }

  @Test
  void getAllWeatherDetails() {
    Mockito.when(cityRepository.findAll()).thenReturn(Flux.just(city));
    webTestClient
        .get()
        .uri("/city")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("[0].name")
        .isEqualTo(city.getName());
  }

  @Test
  void getWeatherDetailsByCity() {
    Mockito.when(cityRepository.findByName(city.getName())).thenReturn(Mono.just(city));
    webTestClient
        .get()
        .uri("/city/{name}", city.getName())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo(city.getName());
  }

  @Test
  void deleteById() {
    var id = city.getId();

    Mockito.when(cityRepository.deleteById(id)).thenReturn(Mono.empty());

    webTestClient
        .delete()
        .uri("/city/{id}", id)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Void.class);
  }

  @Test
  void updateWeather() {

    Mockito.when(cityRepository.save(Mockito.any())).thenReturn(Mono.just(city));
    webTestClient.put().uri("/city/update").bodyValue(city).exchange().expectStatus().isOk();
  }
}
