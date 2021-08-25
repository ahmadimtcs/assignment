package com.clairvoyant.weather.handler;

import com.clairvoyant.weather.model.City;
import com.clairvoyant.weather.repository.CityRepository;
import com.clairvoyant.weather.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CityHandler {
    @Autowired
    private CityRepository repository;

    @Autowired
    private CityService cityService;


    public CityHandler(CityRepository repository, CityService cityService) {
        this.repository = repository;
        this.cityService = cityService;
    }

    public Mono<ServerResponse> getCityList(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cityService.getAllBooks(), City.class);
    }

    public Mono<ServerResponse> getCityByName(ServerRequest request) {
        String name = request.pathVariable("name");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cityService.getCityByName(name), City.class);
    }

    public Mono<ServerResponse> updateCity(ServerRequest request) {
        return request.bodyToMono(City.class)
                .flatMap(city -> cityService.updateCity(city))
                .flatMap(modCity -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(modCity)));
    }

    public Mono<ServerResponse> deleteCityById(ServerRequest request) {
                        Long id = Long.valueOf(request.pathVariable("id"));
                        return ServerResponse.ok().body(cityService.deleteCityById(id), Void.class);
        }


}




