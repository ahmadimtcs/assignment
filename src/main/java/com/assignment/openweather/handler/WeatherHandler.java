package com.assignment.openweather.handler;

import com.assignment.openweather.domain.service.WeatherService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WeatherHandler {

  @Autowired
  private WeatherService weatherService;


  public Mono<ServerResponse> persist(ServerRequest serverRequest) {
//    return serverRequest.bodyToMono(LocationDTO.class).
//        .doOnNext(this::validate)
//        .doOnNext(location -> weatherService::persist)
//        .flatMap(savedReview ->
//            ServerResponse.status(HttpStatus.CREATED)
//                .bodyValue(savedReview));
    return null;
  }

  public Mono<ServerResponse> search(ServerRequest serverRequest) {
    Optional<String> searchParam = serverRequest.queryParam("search");
    return null;
  }

  public Flux<ServerResponse> getAll(ServerRequest serverRequest) {
    Optional<String> searchParam = serverRequest.queryParam("search");
    return null;
  }

  public Mono<ServerResponse> get(ServerRequest serverRequest) {
    String locationName = serverRequest.pathVariable("locationName");
    return null;
  }

  private void validate(String locationDTO) {

  }
}
