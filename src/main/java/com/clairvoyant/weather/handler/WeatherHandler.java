package com.clairvoyant.weather.handler;

import com.clairvoyant.weather.dto.WeatherDto;
import com.clairvoyant.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 23-08-2021 12:26
 */
@Component
@Slf4j
public class WeatherHandler {

  private final WeatherService weatherService;

  @Autowired
  public WeatherHandler(WeatherService weatherService) {
    super();
    this.weatherService = weatherService;
  }

  /**
   * 25-May-2021 |Get All Weather Details
   */
  public Mono<ServerResponse> getAllWeatherDetails() {
    log.info("Inside Class WeatherHandler Method getAllWeatherDetails");
    Flux<WeatherDto> weatherDetails = weatherService.findAll();
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(weatherDetails, WeatherDto.class)
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  /**
   * 25-May-2021 |Create Weather Detail
   */
  public Mono<ServerResponse> createWeatherDetails(final ServerRequest serverRequest) {
    log.info("Inside Class WeatherHandler Method createWeatherDetails");
    Mono<WeatherDto> weatherDto = serverRequest.bodyToMono(WeatherDto.class);
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(weatherService.createWeatherDetails(weatherDto), WeatherDto.class)
        .switchIfEmpty(ServerResponse.notFound().build());

  }

  /**
   * 25-May-2021 |Update Weather Detail By Id
   */
  public Mono<ServerResponse> updateWeatherDetails(final ServerRequest serverRequest) {
    log.info("Inside Class WeatherHandler Method updateWeatherDetails");
    final String id = serverRequest.pathVariable("id");
    Mono<WeatherDto> weatherDto = serverRequest.bodyToMono(WeatherDto.class);
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(weatherService.updateWeatherDetails(Long.parseLong(id), weatherDto), WeatherDto.class)
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  /**
   * 25-May-2021 | Weather Detail By City Name
   */
  public Mono<ServerResponse> getWeatherDetailsByCity(final ServerRequest serverRequest) {
    log.info("Inside Class WeatherHandler Method getWeatherDetailsByCity");
    final String city = serverRequest.pathVariable("city");
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(weatherService.getWeatherDetailsByCity(city), WeatherDto.class)
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  /**
   * 25-May-2021 |Delete Weather Detail By Id
   */
  public Mono<ServerResponse> deleteWeatherDetailsById(final ServerRequest serverRequest) {
    log.info("Inside Class WeatherHandler Method deleteWeatherDetailsById");
    final String id = serverRequest.pathVariable("id");
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(weatherService.deleteWeatherDetailsById(Long.parseLong(id)), Void.class);
  }
}
