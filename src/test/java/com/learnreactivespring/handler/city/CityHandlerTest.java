package com.learnreactivespring.handler.city;

import com.learnreactivespring.model.City;
import com.learnreactivespring.repository.CityRepository;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
class CityHandlerTest {

  @Autowired
  CityRepository cityRepository;

  @Autowired
  WebTestClient webTestClient;

  String AuthToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWtlc2FqMyIsImlhdCI6MTYzMDMyNzk4MywiZXhwIjoxNjMwMzQ1OTgzfQ.QV-YMXOgmJXIVrAlDbpbv8At-A76i_vQ1PuFxuFQ46E";

  @BeforeEach
  void addCityData() {

    City cityTo = new City("toronto");
    City cityBln = new City("berlin");

    Flux<City> citiesFlux = Flux.just(cityTo, cityBln);

    cityRepository.saveAll(citiesFlux)
        .doOnNext((item -> {
          System.out.println("Inserted item is : " + item);
        })).blockLast();
  }


  @AfterEach
  void tearDown() {
    cityRepository.deleteAll();
  }


  @Test
  void createCity() {

    HashMap<String, String> map = new HashMap<>();
    map.put("cityName", "brazil");

    webTestClient
        .post()
        .uri("/createcity")
        .body(Mono.just(map), City.class)
        .header("Authorization", AuthToken)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void createCityDuplicate() {

    HashMap<String, String> map = new HashMap<>();
    map.put("cityName", "toronto");

    Flux<String> responseFlux = webTestClient
        .post()
        .uri("/createcity")
        .body(Mono.just(map), City.class)
        .header("Authorization", AuthToken)
        .exchange()
        .expectStatus()
        .isOk().returnResult(String.class).getResponseBody();

    StepVerifier.create(responseFlux)
        .expectSubscription()
        .expectNext("City already exist")
        .verifyComplete();
  }


  @Test
  void createCities() {

    List<City> citiList = Arrays.asList(new City("mumbai"), new City("pune"));
    Flux<City> citesFlux = Flux.fromIterable(citiList);

    webTestClient
        .post()
        .uri("/createcities")
        .body(citesFlux, City.class)
        .header("Authorization", AuthToken)
        .exchange()
        .expectStatus()
        .isOk();
  }


  @Test
  void getCity() {

    webTestClient.get().uri("/getcity/toronto")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectBody(City.class);
  }


  @Test
  void getCityNotFound() {

    webTestClient.get().uri("/getcity/tokyo")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  void getAllCities() {

    Flux<City> cityFlux = webTestClient.get().uri("/getallcities")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk().returnResult(City.class).getResponseBody();

    StepVerifier.create(cityFlux)
        .expectSubscription()
        .expectNext(new City("toronto"))
        .expectNext(new City("berlin"))
        .verifyComplete();
  }

  @Test
  void updateCity() {

    HashMap<String, String> map = new HashMap<>();
    map.put("cityName", "vancouver");

    webTestClient
        .put()
        .uri("/updatecity/toronto")
        .body(Mono.just(map), City.class)
        .header("Authorization", AuthToken)
        .exchange()
        .expectStatus()
        .isOk();
  }

  @Test
  void updateCityNotFound() {

    HashMap<String, String> map = new HashMap<>();
    map.put("cityName", "brampton");

    webTestClient
        .put()
        .uri("/updatecity/chicago")
        .body(Mono.just(map), City.class)
        .header("Authorization", AuthToken)
        .exchange()
        .expectStatus()
        .isNotFound();
  }


  @Test
  void deleteCity() {
    webTestClient.delete().uri("/deletecity/toronto")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  void deleteCityNotFound() {
    webTestClient.delete().uri("/deletecity/tokyo")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isNotFound();
  }
}