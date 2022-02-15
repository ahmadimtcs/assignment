package com.assignment.openweather.domain.service;

import com.assignment.openweather.domain.model.dto.LocationDataResponseDTO;
import com.assignment.openweather.rest.model.LocationDTO;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WeatherService {
  Mono<List<String>> persist(LocationDTO locations);

  Mono<LocationDataResponseDTO> get(String locationName);

  Flux<LocationDataResponseDTO> get();

  void delete(String locationName);
}
