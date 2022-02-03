package com.assignment.openweather.routes;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.assignment.openweather.handler.WeatherHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WeatherRouter {

  public RouterFunction<ServerResponse> weatherRoute(WeatherHandler weatherHandler) {
    return route()
        .nest(path("/v1/weather"), builder ->
            builder
                .POST("/locations", weatherHandler::persist)
                .GET("/find", weatherHandler::search)).build();
  }
}
