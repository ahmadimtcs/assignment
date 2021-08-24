package com.weather.assignment.handler;

import com.weather.assignment.document.WeatherElements;
import com.weather.assignment.repository.WeatherRepository;
import com.weather.assignment.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class WeatherHandler {

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    WeatherService weatherService;

    @Value("${time.period.min}")
    private int timeInterval;

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public  Mono<ServerResponse> getWeatherDetails(ServerRequest request) {
        Flux<WeatherElements> weatherElements = weatherRepository.findAll();
        return weatherElements.map(elements->{
            if(elements == null){
                weatherService.getWeather();
            } else if((elements.getDate().getTime() + (timeInterval * 60000)) < (new Date().getTime())){
                weatherService.getWeather();
            }
            return elements;
        }).then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(weatherRepository.findAll(), WeatherElements.class));
    }

    public Mono<ServerResponse> createWeatherDetails(ServerRequest serverRequest){
        Mono<WeatherElements> weatherElementsMono = serverRequest.bodyToMono(WeatherElements.class);
        return weatherElementsMono.flatMap(weatherElements -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).
                body(weatherRepository.save(weatherElements),WeatherElements.class));
    }

    public Mono<ServerResponse> deleteWeatherDetails(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<Void> deletedData = weatherRepository.deleteById(Long.parseLong(id));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deletedData,Void.class);
    }

    public  Mono<ServerResponse> updateWeatherDetails(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<WeatherElements> updatedWeather = request.bodyToMono(WeatherElements.class).flatMap(weatherElements -> {
            Mono<WeatherElements> weatherElementsMono = weatherRepository.findById(Long.parseLong(id)).flatMap(currentElement -> {
                currentElement.setName(weatherElements.getName());
                return weatherRepository.save(currentElement);
            });
            return weatherElementsMono;
        });
        return updatedWeather.flatMap(weatherElements -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(weatherElements)).switchIfEmpty(notFound));
    }
}
