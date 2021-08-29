package com.weather.assignment.service;

import com.weather.assignment.dto.WeatherDetailsDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface WeatherService {

  void getWeatherDetails();

  Mono<WeatherDetailsDto> createWeatherDetails(Mono<WeatherDetailsDto> weatherDetailsMono);

  Flux<WeatherDetailsDto> findAll();

  Mono<Void> deleteWeatherDetailsById(Long id);

  Mono<WeatherDetailsDto> updateWeatherDetails(Long id, Mono<WeatherDetailsDto> updatedWeather);
}
