package com.learnreactivespring.handler.weather;

import com.learnreactivespring.model.Weather;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IWeatherHandler {

  Mono<Weather> createWeather(Weather weather);

  Mono<ServerResponse> getCurrentWeather(ServerRequest request);

  Mono<Weather> getCurrentWeatherOpenApi(String cityName);

  Mono<Weather> getZipCodeWeatherDB(String cityName);


}
