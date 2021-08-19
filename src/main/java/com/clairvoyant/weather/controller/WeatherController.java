package com.clairvoyant.weather.controller;

import static com.clairvoyant.weather.contants.WeatherConstants.WEATHER_END_POINT;

import com.clairvoyant.weather.document.WeatherDetails;
import com.clairvoyant.weather.dto.WeatherDto;
import com.clairvoyant.weather.exception.NotFoundException;
import com.clairvoyant.weather.repository.WeatherRepository;
import com.clairvoyant.weather.service.GenerateWeatherDataService;
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

  @Autowired
  WeatherRepository weatherRepository;

  @Autowired
  GenerateWeatherDataService generateWeatherDataService;

  /**
   * Created By Gufran Khan | 17-May-2021  |Get All Weather Details
   */
  @GetMapping(WEATHER_END_POINT)
  public Flux<WeatherDetails> getAllWeatherDetails() {
    log.info("Inside Class WeatherController Method getAllWeatherDetails");
    generateWeatherDataService.refreshAfterTime();
    return weatherRepository.findAll();
  }


  /**
   * Created By Gufran Khan | 17-May-2021  |Create Weather Detail
   */
  @PostMapping(WEATHER_END_POINT)
  public Mono<WeatherDetails> createWeatherDetails(
      @RequestBody Mono<WeatherDetails> weatherDetails) {
    log.info("Inside Class WeatherController Method createWeatherDetails");
    generateWeatherDataService.refreshAfterTime();
    return weatherDetails.flatMap(result ->
        weatherRepository.save(result)
    );
  }

  /**
   * Created By Gufran Khan | 17-May-2021  |Update Book Detail By Id
   */
  @PutMapping(WEATHER_END_POINT)
  public Mono<ResponseEntity<WeatherDetails>> updateWeather(
      @RequestBody WeatherDto weatherDetail) {

    log.info("Inside Class WeatherController Method updateWeather");
    generateWeatherDataService.refreshAfterTime();
    return weatherRepository.findById(weatherDetail.getId())
        .flatMap(result -> {
          result.setName(weatherDetail.getName());
          return weatherRepository.save(result);
        })
        .map(updatedItem -> new ResponseEntity<>(updatedItem, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * Created By Gufran Khan | 17-May-2021  |Get Book Detail By City Name
   */
  @GetMapping(WEATHER_END_POINT + "/city/{city}")
  public Mono<ResponseEntity<WeatherDetails>> getWeatherDetailsByCity(@PathVariable String city) {
    log.info("Inside Class WeatherController Method getWeatherDetailsByCity");
    generateWeatherDataService.refreshAfterTime();
    return weatherRepository.findByName(city)
        .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
        .switchIfEmpty(Mono.error(new NotFoundException("NotFoundException  message!")));
  }

  /**
   * Created By Gufran Khan | 17-May-2021  |Delete Weather Detail By Id
   */
  @DeleteMapping(WEATHER_END_POINT + "/{id}")
  public Mono<Void> deleteWeatherDetails(@PathVariable Long id) {
    log.info("Inside Class WeatherController Method deleteWeatherDetails");
    generateWeatherDataService.refreshAfterTime();
    return weatherRepository.deleteById(id);
  }


}

