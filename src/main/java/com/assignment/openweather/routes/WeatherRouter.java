package com.assignment.openweather.routes;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.assignment.openweather.domain.model.dto.SearchDataResponseDTO;
import com.assignment.openweather.domain.model.dto.WeatherDataResponseDTO;
import com.assignment.openweather.handler.WeatherHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

@Configuration
public class WeatherRouter {

  public RouterFunction<ServerResponse> weatherRoute(WeatherHandler weatherHandler) {
    return route(GET("/v1/locations/weather"),
            req -> ok().body(
                    weatherHandler.search(req), SearchDataResponseDTO.class))
            .and(route(POST("/v1/locations/weather"),
                    req -> ok().body(
                            weatherHandler.persist(req), WeatherDataResponseDTO.class)))
            .and(route(GET("/v1/locations"),
                    req -> ok().body(
                            weatherHandler.getAll(req), List.class)))
            .and(route(GET("/v1/locations/{locationName}"),
                    req -> ok().body(
                            weatherHandler.get(req), List.class)));
  }
}
