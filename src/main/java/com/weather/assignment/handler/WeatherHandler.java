package com.weather.assignment.handler;

import com.weather.assignment.dto.WeatherDetailsDto;
import com.weather.assignment.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class WeatherHandler {

  private final WeatherService weatherService;

  private static final long MILLISECONDS = 60000;

  @Autowired
  public WeatherHandler(WeatherService weatherService) {
    super();
    this.weatherService = weatherService;
  }

  @Value("${time.period.min}")
  private int timeInterval;

  private static final Mono<ServerResponse> notFound = ServerResponse.notFound().build();

  /** @return Returning a weather details for a resp city */
  public Mono<ServerResponse> getWeatherDetails(ServerRequest request) {
    Flux<WeatherDetailsDto> weatherDetails = weatherService.findAll();
    log.info("In handler to Get weather details" + weatherDetails);
    return weatherDetails
        .map(
            details -> {
              if (details == null
                  || ((details.getDate().getTime() + (timeInterval * MILLISECONDS))
                      < (new Date().getTime()))) {
                weatherService.getWeatherDetails();
              }
              return details;
            })
        .then(
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(weatherService.findAll(), WeatherDetailsDto.class));
  }

  /** @return Create weather details */
  public Mono<ServerResponse> createWeatherDetails(ServerRequest serverRequest) {
    log.info("In handler to Create new weather details");
    Mono<WeatherDetailsDto> weatherDetailsDto = serverRequest.bodyToMono(WeatherDetailsDto.class);
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(weatherService.createWeatherDetails(weatherDetailsDto), WeatherDetailsDto.class);
  }

  /** @return To delete weather details by id */
  public Mono<ServerResponse> deleteWeatherDetails(ServerRequest request) {
    String id = request.pathVariable("id");
    log.info("In handler to delete the weather detail for" + id);
    Mono<Void> deletedData = weatherService.deleteWeatherDetailsById(Long.parseLong(id));
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(deletedData, Void.class);
  }

  /** @return To update weather details */
  public Mono<ServerResponse> updateWeatherDetails(ServerRequest request) {
    String id = request.pathVariable("id");
    log.info("In handler to Update weather detail" + id);
    Mono<WeatherDetailsDto> updatedWeather = request.bodyToMono(WeatherDetailsDto.class);
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            weatherService.updateWeatherDetails(Long.valueOf(id), updatedWeather),
            WeatherDetailsDto.class)
        .switchIfEmpty(notFound);
  }
}
