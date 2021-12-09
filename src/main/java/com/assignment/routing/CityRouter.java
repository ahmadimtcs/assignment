package com.assignment.routing;

import com.assignment.handler.CityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CityRouter {

    @Bean
    public RouterFunction<ServerResponse> reviewsRoute(CityHandler handler) {
        return route()
                .nest(path("/city"), builder ->{
                    builder
                            .GET("", handler::getCityList)
                            .GET("/{name}", RequestPredicates.accept(MediaType.APPLICATION_JSON),handler::getCityByName)
                            .PUT("/update", RequestPredicates.contentType(MediaType.APPLICATION_JSON), handler::updateCity)
                            .DELETE("/{id}",RequestPredicates.accept(MediaType.TEXT_PLAIN), handler::deleteCityById);})

                .build();
    }

}

