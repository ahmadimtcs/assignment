package com.clairvoyant.weather.router;

import com.clairvoyant.weather.handler.CityHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CityRouter {

  @Value("${CITY_WEATHER}")
  private String apiId;

  @Bean
  public RouterFunction<ServerResponse> route(CityHandler handler) {

    return RouterFunctions.route()
        .GET(apiId, handler::getCityList)
        .GET(apiId + "/{name}", RequestPredicates.accept(MediaType.APPLICATION_JSON),
            handler::getCityByName)
        .PUT(apiId, RequestPredicates.contentType(MediaType.APPLICATION_JSON),
            handler::updateCity)
        .DELETE(apiId + "/{id}", RequestPredicates.accept(MediaType.TEXT_PLAIN),
            handler::deleteCityById)
        .build();

  }
}
