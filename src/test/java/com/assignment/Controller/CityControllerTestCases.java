package com.assignment.Controller;

import com.assignment.controller.CityController;
import com.assignment.entity.City;
import com.assignment.handler.CityHandler;
import com.assignment.repository.CityRepository;
import com.assignment.routing.CityRouter;
import com.assignment.service.CityService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {CityService.class, CityController.class, CityRepository.class})
@WebFluxTest
@AutoConfigureWebTestClient
public class CityControllerTestCases {

  @Autowired private WebTestClient webTestClient;

  @MockBean private CityService cityService;
  private static City city;

  @BeforeAll
  public static void setup() {
    City city1 = new City();
    city1.setId(1259229L);
    city1.setName("Pune");
    city1.setTemp(297.08);
    city1.setFeels_like(296.91);
    city1.setTemp_min(297.08);
    city1.setTemp_max(297.08);
    city1.setPressure(1016.0);
    city1.setHumidity(53L);
    city1.setSea_level(1016L);
    city1.setGrnd_level(954L);
    city1.setCountry("IN");
    city = city1;
  }

  @Test
  void getWeatherDetailsByCity() {
    Mockito.when(cityService.getCityByName(city.getName())).thenReturn(Mono.just(city));
    webTestClient
        .get()
        .uri("/cityByName/{name}", city.getName())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo(city.getName());
  }
}
