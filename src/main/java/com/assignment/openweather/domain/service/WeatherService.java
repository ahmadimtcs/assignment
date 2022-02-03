package com.assignment.openweather.domain.service;

import com.assignment.openweather.domain.model.dto.WeatherDataResponseDTO;
import com.assignment.openweather.rest.model.LocationDTO;
import java.util.List;
import reactor.core.publisher.Mono;

public interface WeatherService {
  Mono<List<String>> persist(LocationDTO locations);

  Mono<WeatherDataResponseDTO> get(String locationName);

}
