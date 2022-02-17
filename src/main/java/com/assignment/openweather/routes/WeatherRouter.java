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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WeatherRouter {

  @Bean
  public RouterFunction<ServerResponse> weatherRoute(WeatherHandler weatherHandler) {
    return RouterFunctions
        .route(RequestPredicates.POST("/v1/locations/weather")
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
            .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), weatherHandler::persist)
        .andRoute(RequestPredicates.GET("/v1/locations")
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
            .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), weatherHandler::getAll)
        .andRoute(RequestPredicates.GET("/v1/locations/{locationName}")
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
            .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), weatherHandler::get)
        .andRoute(RequestPredicates.DELETE("/v1/locations/{locationName}")
            .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
            .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), weatherHandler::delete);
  }
}
