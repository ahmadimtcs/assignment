package com.assignment.openweather.handler;

import com.assignment.openweather.domain.model.dto.LocationDataResponseDTO;
import com.assignment.openweather.domain.service.WeatherService;
import com.assignment.openweather.rest.model.LocationDTO;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class WeatherHandler {

  private WeatherService weatherService;

  public Mono<ServerResponse> persist(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(LocationDTO.class)
        .flatMap(weatherService::persist)
        .flatMap(location ->
            ServerResponse.status(HttpStatus.CREATED)
                .bodyValue(location));
  }

  public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
    Flux<LocationDataResponseDTO> locationDataResponseDTOFlux = weatherService.get();
    return ServerResponse.ok().body(locationDataResponseDTOFlux, LocationDataResponseDTO.class);
  }

  public Mono<ServerResponse> get(ServerRequest serverRequest) {
    String locationName = serverRequest.pathVariable("locationName");
    Mono<LocationDataResponseDTO> locationDataResponseDTOMono = weatherService.get(locationName);
    return ServerResponse.ok().body(locationDataResponseDTOMono, LocationDataResponseDTO.class);
  }

  public Mono<ServerResponse> delete(ServerRequest serverRequest) {
    String locationName = serverRequest.pathVariable("locationName");
    weatherService.delete(locationName);
    return ServerResponse.noContent().build();
  }
}
