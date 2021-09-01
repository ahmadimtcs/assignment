package com.learnreactivespring.handler.city;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import com.learnreactivespring.model.City;
import com.learnreactivespring.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityHandler implements ICityHandler {

  static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

  @Autowired
  CityRepository cityRepository;

  public Mono<ServerResponse> createCity(ServerRequest request) {

    Mono<City> cityRequest = request.bodyToMono(City.class);

    return cityRequest.flatMap(
        city -> cityRepository.findById(city.getCityName())
            .flatMap(da ->
                ServerResponse.ok().contentType(APPLICATION_JSON)
                    .body(BodyInserters.fromValue("City already exist"))
            )
            .switchIfEmpty(cityRepository.save(city).flatMap(city1 ->
                ServerResponse.ok().contentType(APPLICATION_JSON)
                    .body(fromValue(city1))
            ))
    );
  }

  public Mono<ServerResponse> createCities(ServerRequest request) {

    Flux<City> cityRequest = request.bodyToFlux(City.class);
    Flux<City> citiesData = cityRequest.flatMap(city -> cityRepository.save(city));

    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(citiesData, City.class);
  }

  public Mono<ServerResponse> getCity(ServerRequest request) {

    String cityName = request.pathVariable("cityName");
    Mono<City> cityMono = cityRepository.findById(cityName);

    return cityMono.flatMap(
        city -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(city))
    ).switchIfEmpty(notFound).log();

  }

  public Mono<ServerResponse> getAllCities(ServerRequest request) {

    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(cityRepository.findAll(),
            City.class);
  }


  public Mono<ServerResponse> updateCity(ServerRequest request) {

    Mono<City> cityRequest = request.bodyToMono(City.class);

    String cityName = request.pathVariable("cityName");
    Mono<City> cityMono = cityRepository.findById(cityName);

    return cityMono.flatMap(
        city -> cityRepository.deleteById(city.getCityName()).then(
            cityRequest.flatMap(newCity -> cityRepository.save(newCity))
                .then(ServerResponse.ok().contentType(APPLICATION_JSON)
                    .body(fromValue("city updated")))
        )
    ).switchIfEmpty(notFound);

  }

  public Mono<ServerResponse> deleteCity(ServerRequest request) {

    String cityName = request.pathVariable("cityName");

    return cityRepository.findById(cityName).flatMap(city -> cityRepository.deleteById(cityName)
        .then(ServerResponse.ok().contentType(APPLICATION_JSON)
            .body(fromValue(city.toString() + " removed")))
    ).switchIfEmpty(notFound);

  }

}
