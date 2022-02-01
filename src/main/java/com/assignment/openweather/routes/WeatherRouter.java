package com.assignment.openweather.routes;

import com.assignment.openweather.handler.WeatherHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WeatherRouter {
    public RouterFunction<ServerResponse> weatherRoute(WeatherHandler weatherHandler){
        return route()
                .nest(path("/v1/weather"), builder ->
                        builder
                                .GET("", weatherHandler::get)
                                .GET("/find", weatherHandler::search)).build();
    }
}
