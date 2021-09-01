package com.learnreactivespring.handler.city;

import com.learnreactivespring.model.City;
import java.util.List;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICityHandler {

  Mono<ServerResponse> createCity(ServerRequest request);

  Mono<ServerResponse> createCities(ServerRequest request);

  Mono<ServerResponse> getCity(ServerRequest request);

  Mono<ServerResponse> getAllCities(ServerRequest request);

  Mono<ServerResponse> updateCity(ServerRequest request);

  Mono<ServerResponse> deleteCity(ServerRequest request);


}
