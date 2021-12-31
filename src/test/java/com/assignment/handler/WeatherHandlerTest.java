package com.assignment.handler;

import com.assignment.entity.City;
import com.assignment.repository.CityRepository;
import com.assignment.routing.CityRouter;
import com.assignment.service.CityService;
import com.assignment.service.GenerateWeatherDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;


@AutoConfigureWebTestClient
@WebFluxTest
@ContextConfiguration(classes = {CityRouter.class, CityHandler.class})
public class WeatherHandlerTest {

  @Autowired private WebTestClient webTestClient;

  @MockBean private CityRepository cityRepository;

  @MockBean private CityService cityService;

  private static City city;

  @BeforeAll
  public static void setup() throws IOException {
    city =
        new ObjectMapper()
            .readValue(new ClassPathResource("data/Weather.json").getFile(), City.class);
  }

  @Test
  void getAllWeatherDetails() {
    Mockito.when(cityService.getCityList()).thenReturn(Flux.just(city));
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
    Mockito.when(cityService.getCityByName(city.getName())).thenReturn(Mono.just(city));
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
  void updateWeather() {

    Mockito.when(cityService.updateCity(Mockito.any())).thenReturn(Mono.just(city));
    webTestClient.put().uri("/city/update").bodyValue(city).exchange().expectStatus().isOk();
  }

  @Test
  void getvalidWeather() {

    Mockito.when(cityService.getCityByName(Mockito.anyString())).thenReturn(Mono.just(city));
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
}
