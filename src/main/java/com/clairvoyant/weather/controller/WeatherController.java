package com.clairvoyant.weather.controller;

import static com.clairvoyant.weather.contants.WeatherConstants.WEATHER_END_POINT;

import com.clairvoyant.weather.dto.WeatherDto;
import com.clairvoyant.weather.exception.NotFoundException;
import com.clairvoyant.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 17-08-2021 16:19
 */

@RestController
@Slf4j
public class WeatherController {

  private final WeatherService weatherService;

  @Autowired
  public WeatherController(WeatherService weatherService) {
    super();
    this.weatherService = weatherService;
  }


  /**
   * 17-May-2021  |Get All Weather Details
   */
  @GetMapping(WEATHER_END_POINT)
  public Flux<WeatherDto> getAllWeatherDetails() {
    log.info("Inside Class WeatherController Method getAllWeatherDetails");
    return weatherService.findAll();
  }

  /**
   * 17-May-2021  |Create Weather Detail
   */
  @PostMapping(WEATHER_END_POINT)
  public Mono<WeatherDto> createWeatherDetails(
      @RequestBody Mono<WeatherDto> weatherDto) {
    log.info("Inside Class WeatherController Method createWeatherDetails");
    return weatherService.createWeatherDetails(weatherDto);
  }

  /**
   * 17-May-2021  |Update Weather Detail By Id
   */
  @PutMapping(WEATHER_END_POINT + "/{id}")
  public Mono<ResponseEntity<WeatherDto>> updateWeatherDetails(@PathVariable("id") Long id,
      @RequestBody Mono<WeatherDto> weatherDto) {

    log.info("Inside Class WeatherController Method updateWeather");
    return weatherService.updateWeatherDetails(id, weatherDto)
        .map(updatedItem -> new ResponseEntity<>(updatedItem, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * 17-May-2021  |Get Weather Detail By City Name
   */
  @GetMapping(WEATHER_END_POINT + "/city/{city}")
  public Mono<ResponseEntity<WeatherDto>> getWeatherDetailsByCity(@PathVariable String city) {
    log.info("Inside Class WeatherController Method getWeatherDetailsByCity");
    return weatherService.getWeatherDetailsByCity(city)
        .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
        .switchIfEmpty(Mono.error(new NotFoundException("NotFoundException  message!")));
  }

  /**
   * 17-May-2021  |Delete Weather Detail By Id
   */
  @DeleteMapping(WEATHER_END_POINT + "/{id}")
  public Mono<Void> deleteWeatherDetails(@PathVariable Long id) {
    log.info("Inside Class WeatherController Method deleteWeatherDetails");
    return weatherService.deleteWeatherDetailsById(id);
  }


}

