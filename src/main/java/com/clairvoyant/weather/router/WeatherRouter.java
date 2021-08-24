package com.clairvoyant.weather.router;

import static com.clairvoyant.weather.contants.WeatherConstants.WEATHER_FUNCTIONAL_END_POINT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.clairvoyant.weather.handler.WeatherHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 23-08-2021 12:26
 */
@Configuration
@Slf4j
public class WeatherRouter {

  @Bean
  public RouterFunction<ServerResponse> weatherRoute(WeatherHandler weatherHandler) {

    return RouterFunctions
        .route(GET(WEATHER_FUNCTIONAL_END_POINT).and(accept(APPLICATION_JSON))
            , weatherHandler::getAllWeatherDetails)
        .andRoute(POST(WEATHER_FUNCTIONAL_END_POINT).and(accept(APPLICATION_JSON))
            , weatherHandler::createWeatherDetails)
        .andRoute(PUT(WEATHER_FUNCTIONAL_END_POINT + "/{id}").and(accept(APPLICATION_JSON))
            , weatherHandler::updateWeatherDetails)
        .andRoute(DELETE(WEATHER_FUNCTIONAL_END_POINT + "/{id}").and(accept(APPLICATION_JSON))
            , weatherHandler::deleteWeatherDetailsById)
        .andRoute(GET(WEATHER_FUNCTIONAL_END_POINT + "/city/{city}").and(accept(APPLICATION_JSON))
            , weatherHandler::getWeatherDetailsByCity);
  }
}
