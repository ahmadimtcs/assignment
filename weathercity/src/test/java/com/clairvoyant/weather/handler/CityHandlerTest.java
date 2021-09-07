package com.clairvoyant.weather.handler;

import com.clairvoyant.weather.model.City;
import com.clairvoyant.weather.repository.CityRepository;
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

@SpringBootTest
@AutoConfigureWebTestClient
class CityHandlerTest {

  @MockBean
  private CityRepository cityRepository;

  @Autowired
  private WebTestClient webTestClient;

  private City city;

  @Test
  @WithMockUser(roles = "ADMIN")
  void getCityByName() {
    City city = new City(2563191L, "Pune", 34.2, 35.8);
    Mockito.when(cityRepository.findByName(city.getName())).thenReturn(Mono.just(city));
    webTestClient.get().uri("/city/Pune").exchange().expectStatus().isOk().expectBody(City.class);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void getCityList() {
    City city1 = new City(2563191L, "Pune", 34.2, 35.8);
    City city2 = new City(2563192L, "Mumbai", 36.2, 38.8);
    Mockito.when(cityRepository.findAll()).thenReturn(Flux.just(city1, city2));
    webTestClient.get().uri("/city").exchange().expectStatus().isOk().expectBodyList(City.class);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateCity() {

    City city = new City(2563191L, "Pune", 34.2, 35.8);
    Mono<City> cityMono = Mono.just(city);
    Mockito.when(cityRepository.save(Mockito.any())).thenReturn(cityMono);
    webTestClient.put().uri("/city").contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(city), City.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody(City.class);

  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteCityById() {
    City city = new City(2563191L, "Pune", 34.2, 35.8);
    Mockito.when(cityRepository.deleteById(2563191L)).thenReturn(Mono.empty());
    webTestClient.delete()
        .uri("/city/2563191")
        .exchange()
        .expectStatus().isOk();

  }
}
