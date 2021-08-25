package com.clairvoyant.weather.service;

import com.clairvoyant.weather.dto.WeatherDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 20-08-2021 17:36
 */
@Service
public interface WeatherService {

	Flux<WeatherDto> findAll();

	Mono<WeatherDto> createWeatherDetails(Mono<WeatherDto> weatherDto);

	Mono<WeatherDto> updateWeatherDetails(Long id, Mono<WeatherDto> weatherDto);

	Mono<WeatherDto> getWeatherDetailsByCity(String city);

	Mono<Void> deleteWeatherDetailsById(Long id);
}
