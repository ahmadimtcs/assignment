package com.mahesh.weather.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import com.mahesh.weather.client.OpenWeatherRestClient;
import com.mahesh.weather.model.City;
import com.mahesh.weather.model.UserCityDetails;
import com.mahesh.weather.repository.CityReactiveRepository;
import com.mahesh.weather.router.WeatherRouter;
import com.mahesh.weather.service.CityService;
import com.mahesh.weather.service.UserCityDetailsService;
import com.mahesh.weather.service.WeatherDetailsService;
import java.util.HashSet;
import java.util.List;
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
class UserCityTest {

  PodamFactory factory = new PodamFactoryImpl();

  @MockBean
  UserCityDetailsService userCityDetailsServiceMock;

  @Autowired
  WebTestClient webTestClient;

  UserCityDetails userCity;
  City city;

  @MockBean
  OpenWeatherRestClient openWeatherRestClientMock;

  @MockBean
  CityService cityServiceMock;

  @MockBean
  WeatherDetailsService weatherDetailsService;

  @MockBean
  CityReactiveRepository cityReactiveRepository;

  static String USER_CITY_ENDPOINT = "/v1/userCity";
  static String USER_CITIES_ENDPOINT = "/v1/userCities";

  @BeforeEach
  void setUp() {
    userCity = new UserCityDetails();
    factory.populatePojo(userCity);
    city = new City("1", "Pune", "18.521428", "73.8544541", "IN");
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  @WithMockUser(username = "user")
  void findCity() {
    when(cityServiceMock.findCityByNameInOpenWeather(isA(City.class)))
        .thenReturn(Mono.just(city));

    webTestClient.mutateWith(csrf())
        .post()
        .uri("/v1/city")
        .bodyValue(city)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(City.class)
        .consumeWith(cityEntityExchangeResult -> {
          var cityInfo = cityEntityExchangeResult.getResponseBody();
          assertEquals(city.getId(), cityInfo.getId());
          assertEquals(city.getName(), cityInfo.getName());
        });
  }

  @Test
  @WithMockUser(username = "user")
  void findCity_Exception() {

    City city1 = City.builder().country(null).name(null).build();

    webTestClient.mutateWith(csrf())
        .post()
        .uri("/v1/city")
        .bodyValue(city1)
        .exchange()
        .expectStatus()
        .is5xxServerError();
  }

  @Test
  @WithMockUser(username = "user")
  void addUserCities() {
    City city1 = new City();
    City city2 = new City();
    factory.populatePojo(city1);
    factory.populatePojo(city2);
    List<City> cities = List.of(city1, city2);

    when(cityServiceMock.findByNameAndCountry(isA(String.class), isA(String.class)))
        .thenReturn(Mono.just(city));

    when(cityServiceMock.checkCityInOpenWeather(isA(City.class)))
        .thenReturn(Mono.just(city));

    when(userCityDetailsServiceMock.findOrCreateUserCityDetails(isA(String.class)))
        .thenReturn(Mono.just(userCity));

    when(userCityDetailsServiceMock.save(isA(UserCityDetails.class)))
        .thenReturn(Mono.just(userCity));

    webTestClient.mutateWith(csrf())
        .post()
        .uri(USER_CITIES_ENDPOINT)
        .bodyValue(cities)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(UserCityDetails.class)
        .consumeWith(userCityDetailsEntityExchangeResult -> {
          var userCityInfo = userCityDetailsEntityExchangeResult.getResponseBody();
          assertEquals(userCity.getId(), userCityInfo.getId());
          assertEquals(userCity.getCities().size(), userCityInfo.getCities().size());
        });
  }

  @Test
  @WithMockUser(username = "user")
  void findAllUserCities() {

    when(userCityDetailsServiceMock.findUserCityDetails(isA(String.class)))
        .thenReturn(Mono.just(userCity));

    webTestClient.mutateWith(csrf())
        .get()
        .uri(USER_CITIES_ENDPOINT)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(UserCityDetails.class)
        .consumeWith(userCityDetailsEntityExchangeResult -> {
          var userCityInfo = userCityDetailsEntityExchangeResult.getResponseBody();
          assertEquals(userCity.getId(), userCityInfo.getId());
          assertEquals(userCity.getCities().size(), userCityInfo.getCities().size());
        });

  }

  @Test
  @WithMockUser(username = "user")
  void addUserCity() {
    UserCityDetails userCityDetails = new UserCityDetails();
    factory.populatePojo(userCityDetails);

    when(cityServiceMock.findByNameAndCountry(isA(String.class), isA(String.class)))
        .thenReturn(Mono.just(city));

    when(cityServiceMock.checkCityInOpenWeather(isA(City.class)))
        .thenReturn(Mono.just(city));

    when(userCityDetailsServiceMock.findOrCreateUserCityDetails(isA(String.class)))
        .thenReturn(Mono.just(userCityDetails));

    when(userCityDetailsServiceMock.save(isA(UserCityDetails.class)))
        .thenReturn(Mono.just(userCity));

    webTestClient.mutateWith(csrf())
        .post()
        .uri(USER_CITY_ENDPOINT)
        .bodyValue(city)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(UserCityDetails.class)
        .consumeWith(userCityDetailsEntityExchangeResult -> {
          var userCityInfo = userCityDetailsEntityExchangeResult.getResponseBody();
          assertEquals(userCity.getId(), userCityInfo.getId());

        });
  }

  @Test
  @WithMockUser(username = "user")
  void addUserCity_Exception() {
    UserCityDetails userCityDetails = new UserCityDetails();
    factory.populatePojo(userCityDetails);

    when(cityServiceMock.findByNameAndCountry(isA(String.class), isA(String.class)))
        .thenReturn(Mono.empty());

    when(cityServiceMock.checkCityInOpenWeather(isA(City.class)))
        .thenReturn(Mono.empty());

    webTestClient.mutateWith(csrf())
        .post()
        .uri(USER_CITY_ENDPOINT)
        .bodyValue(city)
        .exchange()
        .expectStatus()
        .is5xxServerError();

  }

  @Test
  @WithMockUser(username = "user")
  void updateUserCities() {
    City city1 = new City();
    City city2 = new City();
    factory.populatePojo(city1);
    factory.populatePojo(city2);
    List<City> cities = List.of(city1, city2);

    when(cityServiceMock.findByNameAndCountry(isA(String.class), isA(String.class)))
        .thenReturn(Mono.just(city));

    when(cityServiceMock.checkCityInOpenWeather(isA(City.class)))
        .thenReturn(Mono.just(city));

    when(userCityDetailsServiceMock.findOrCreateUserCityDetails(isA(String.class)))
        .thenReturn(Mono.just(userCity));

    when(userCityDetailsServiceMock.save(isA(UserCityDetails.class)))
        .thenReturn(Mono.just(userCity));

    webTestClient.mutateWith(csrf())
        .put()
        .uri(USER_CITIES_ENDPOINT)
        .bodyValue(cities)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(UserCityDetails.class)
        .consumeWith(userCityDetailsEntityExchangeResult -> {
          var userCityInfo = userCityDetailsEntityExchangeResult.getResponseBody();
          assertEquals(userCity.getId(), userCityInfo.getId());
          assertEquals(userCity.getCities().size(), userCityInfo.getCities().size());
        });
  }

  @Test
  @WithMockUser(username = "user")
  void deleteUserCities() {
    when(userCityDetailsServiceMock.findUserCityDetails(isA(String.class)))
        .thenReturn(Mono.just(userCity));
    when(userCityDetailsServiceMock.removeById(isA(String.class)))
        .thenReturn(Mono.empty());

    webTestClient.mutateWith(csrf())
        .delete()
        .uri(USER_CITIES_ENDPOINT)
        .exchange()
        .expectStatus()
        .is2xxSuccessful();
  }

  @Test
  @WithMockUser(username = "user")
  void deleteUserCities_NotPresent() {
    when(userCityDetailsServiceMock.findUserCityDetails(isA(String.class)))
        .thenReturn(Mono.empty());

    webTestClient.mutateWith(csrf())
        .delete()
        .uri(USER_CITIES_ENDPOINT)
        .exchange()
        .expectStatus()
        .is5xxServerError();

  }

  @Test
  @WithMockUser(username = "user")
  void removeAllCitiesForUser() {
    UserCityDetails userCityDetails = new UserCityDetails();
    userCityDetails.setId("123");
    userCityDetails.setCities(new HashSet<>());
    userCityDetails.setUserId("user");

    when(userCityDetailsServiceMock.findOrCreateUserCityDetails(isA(String.class)))
        .thenReturn(Mono.just(userCity));

    when(userCityDetailsServiceMock.save(isA(UserCityDetails.class)))
        .thenReturn(Mono.just(userCityDetails));

    webTestClient.mutateWith(csrf())
        .delete()
        .uri(USER_CITIES_ENDPOINT + "/user")
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(UserCityDetails.class)
        .consumeWith(userCityDetailsEntityExchangeResult -> {
          var userCityInfo = userCityDetailsEntityExchangeResult.getResponseBody();
          assertEquals("123", userCityInfo.getId());
          assertEquals(0, userCityInfo.getCities().size());
        });
  }

}