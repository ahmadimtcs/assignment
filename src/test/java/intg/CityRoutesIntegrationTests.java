import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mahesh.weather.client.OpenWeatherRestClient;
import com.mahesh.weather.handler.UserCityHandler;
import com.mahesh.weather.model.City;
import com.mahesh.weather.model.UserCityDetails;
import com.mahesh.weather.repository.CityReactiveRepository;
import com.mahesh.weather.repository.UserCityDetailsReactiveRepository;
import com.mahesh.weather.router.WeatherRouter;
import com.mahesh.weather.service.CityService;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
    , classes = {UserCityDetailsReactiveRepository.class, CityReactiveRepository.class,
    WeatherRouter.class, UserCityHandler.class,
    WebClient.class, OpenWeatherRestClient.class}
)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
//@Import(value = {WebClientConfig.class})
public class CityRoutesIntegrationTests {

  @Autowired
  WebTestClient webTestClient;

  @Autowired
  CityReactiveRepository cityReactiveRepository;

  @Autowired
  UserCityDetailsReactiveRepository userCityDetailsReactiveRepository;

  @Autowired
  CityService cityService;

  @MockBean
  WebClient webClient;

  static String USER_CITY_URL = "/v1/userCities";

  @BeforeEach
  void setup() {
    var citySet = Set.of(new City("1", "Mumbai", "19.0759899", "72.8773928", "IN"));
    var userCity = new UserCityDetails("11", "user", citySet);

    cityReactiveRepository.saveAll(Flux.fromIterable(citySet)).blockLast();
    userCityDetailsReactiveRepository.saveAll(Flux.fromIterable(List.of(userCity))).blockLast();
  }

  @AfterEach
  void tearDown() {
    userCityDetailsReactiveRepository.deleteAll().block();
    cityReactiveRepository.deleteAll().block();
  }

  //@Test
  void findAllUserCities() {

    webTestClient.get()
        .uri(USER_CITY_URL)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(UserCityDetails.class)
        .consumeWith(userCityDetailsEntityExchangeResult -> {
          var userCityDetails = userCityDetailsEntityExchangeResult.getResponseBody();
          assertEquals("user", userCityDetails.getUserId());
          assertEquals(1, userCityDetails.getCities().size());
        });
  }
}
