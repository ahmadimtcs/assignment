package com.assignment;

import com.assignment.entity.City;
import com.assignment.handler.CityHandler;
import com.assignment.repository.CityRepository;
import com.assignment.routing.CityRouter;
import com.assignment.service.CityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;

@AutoConfigureWebTestClient
@WebFluxTest
@ContextConfiguration(classes = {CityRouter.class, CityHandler.class})
class WhetherApplicationTests {

  @Autowired private WebTestClient webTestClient;

  @MockBean private CityRepository cityRepository;

  @MockBean private CityService cityService;

  @Test
  void getCityList() {

    var cityList =
        new City(1259229L, "Pune", 297.08, 296.91, 297.08, 297.08, 1016.0, 53L, 1016L, 954L, "IN");
    Mockito.when(cityService.getCityList()).thenReturn(Flux.just(cityList));

    // when
    webTestClient.get().uri("/city").exchange().expectStatus().isOk();
  }

  @Test
  void deleteCityById() {
    // given
    var cityId = 1259229L;
    var name = "Pune";
    var cityList =
        new City(1259229L, "Pune", 297.08, 296.91, 297.08, 297.08, 1016.0, 53L, 1016L, 954L, "IN");

    Mockito.when(cityService.getCityByName(name)).thenReturn(Mono.just(cityList));
    Mockito.when(cityService.deleteCityById((Long) any())).thenReturn(Mono.empty());

    webTestClient
        .delete()
        .uri("/city/{id}", cityId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Void.class);
  }
}
