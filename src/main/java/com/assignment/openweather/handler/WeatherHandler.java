package com.assignment.openweather.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class WeatherHandler {


    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> search(ServerRequest serverRequest) {
        Optional<String> searchParam = serverRequest.queryParam("search");
        return null;
    }
}
