package com.assignment.handler;
import com.assignment.entity.City;
import com.assignment.service.CityService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CityHandler {



    private CityService cityService;


    public CityHandler(CityService cityService) {
        this.cityService = cityService;
    }

    public Mono<ServerResponse> getCityList(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cityService.getCityList(), City.class);
    }

    public Mono<ServerResponse> getCityByName(ServerRequest request) {
        String cityName = request.pathVariable("name");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cityService.getCityByName(cityName), City.class);
    }

    public Mono<ServerResponse> updateCity(ServerRequest request) {
        return request.bodyToMono(City.class)
                .flatMap(cityService::updateCity)
                .flatMap(updatedCity -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(updatedCity)));
    }

    public Mono<ServerResponse> deleteCityById(ServerRequest request) {
        Long cityId = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok().body(cityService.deleteCityById(cityId), Void.class);
    }


}




