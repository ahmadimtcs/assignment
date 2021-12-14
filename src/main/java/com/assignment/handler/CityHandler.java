package com.assignment.handler;
import com.assignment.entity.City;
import com.assignment.exception.CityNotFoundException;
import com.assignment.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CityHandler {



    private CityService cityService;


    public CityHandler(CityService cityService) {
        this.cityService = cityService;
    }

    public Mono<ServerResponse> getCityList(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cityService.getCityList(), City.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

//    public Mono<ResponseEntity<City>> getCityByName(ServerRequest request) {
//        String cityName = request.pathVariable("name");
//
//        return cityService.getCityByName(cityName)
//                .map(movieInfo1 -> ResponseEntity.ok()
//                        .body(movieInfo1))
//                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
//
////        return cityService.getCityByName(cityName)
////                .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
////                .switchIfEmpty(Mono.error(new CityNotFoundException("NotFoundException  message!")));
////        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
////                .body(cityService.getCityByName(cityName), City.class)
////                .switchIfEmpty(Mono.error(new CityNotFoundException("not nount")));
//
//
//
//    }

    public Mono<ServerResponse> getCityByName(final ServerRequest serverRequest) {
        log.info("Inside Class WeatherHandler Method getWeatherDetailsByCity");
        final String cityName = serverRequest.pathVariable("name");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cityService.getCityByName(cityName), City.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }




    private Mono<ServerResponse> buildCityResponse(Mono<City> cityBynameflix) {
        return ServerResponse.ok().body(cityBynameflix, City.class);
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


    public Mono<ServerResponse> cityEx(ServerRequest serverRequest) {
        throw new RuntimeException("RuntimeException Occurred");
    }

}




