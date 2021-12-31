package com.assignment.controller;

import com.assignment.entity.City;
import com.assignment.exception.CityNotFoundException;
import com.assignment.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class CityController {

  private CityService cityService;

  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  @GetMapping("/cityByName/{name}")
  public Mono<ResponseEntity<City>> getCityByName(@PathVariable String name) {
    log.info("Inside Class WeatherController Method getWeatherDetailsByCity");
    return cityService
        .getCityByName(name)
        .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
        .switchIfEmpty(Mono.error(new CityNotFoundException("NotFoundException  message!")));
  }
}
