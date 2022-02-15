package com.assignment.openweather.routes;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.assignment.openweather.domain.model.dto.LocationDataResponseDTO;
import com.assignment.openweather.domain.model.dto.SearchDataResponseDTO;
import com.assignment.openweather.handler.WeatherHandler;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WeatherRouter {

  public RouterFunction<ServerResponse> weatherRoute(WeatherHandler weatherHandler) {
    return route(POST("/v1/locations/weather"),
            req -> ok().body(
                weatherHandler.persist(req), LocationDataResponseDTO.class))
        .and(route(GET("/v1/locations"),
            req -> ok().body(
                weatherHandler.getAll(req), List.class)))
        .and(route(GET("/v1/locations/{locationName}"),
            req -> ok().body(
                weatherHandler.get(req), List.class)))
        .and(route(DELETE("/v1/locations/{locationName}"),
            req -> {
              weatherHandler.delete(req);
              return noContent().build();
            }));
  }
}
