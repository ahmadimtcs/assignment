package com.learnreactivespring.handler.weather;

import static com.learnreactivespring.constants.EndpointConstants.STREAM_WEATHER_END_POINT;
import static com.learnreactivespring.constants.EndpointConstants.WEATHER_CITY_END_POINT;
import static org.junit.jupiter.api.Assertions.*;

import com.learnreactivespring.model.User;
import com.learnreactivespring.model.Weather;
import com.learnreactivespring.repository.WeatherRepository;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
class WeatherHandlerTest {

  @Autowired
  WeatherRepository weatherRepository;

  @Autowired
  WeatherHandler weatherHandler;

  @Autowired
  WebTestClient webTestClient;

  String AuthToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWtlc2FqMyIsImlhdCI6MTYzMDUwNjA5OCwiZXhwIjoxNjMwNTI0MDk4fQ.30SvvmrJmOd6oql_lRgAFfFXHxNf_J-NDjMoSZVfYE4";


  @BeforeEach
  void createInitData() {

    Weather weather = new Weather();
    Weather.MainModel main = new Weather.MainModel();
    main.temp = 11.2;

    weather.setName("mumbai");
    weather.setMain(main);

    Mono<Weather> weatherMono = Mono.just(weather);
    weatherRepository.saveAll(weatherMono).blockLast();
  }


  @Test
  void streamWeather() {

    webTestClient.get().uri(STREAM_WEATHER_END_POINT)
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_STREAM_JSON)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Weather.class);
  }

  @Test
  void getCurrentWeather() {

    webTestClient.get().uri("/weather/mumbai")
        .header("Authorization", AuthToken)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk().expectBody(Weather.class);
  }


}