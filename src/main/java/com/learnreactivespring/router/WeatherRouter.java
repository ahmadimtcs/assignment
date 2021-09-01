package com.learnreactivespring.router;

import static com.learnreactivespring.constants.EndpointConstants.STREAM_WEATHER_END_POINT;
import static com.learnreactivespring.constants.EndpointConstants.WEATHER_CITY_END_POINT;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.learnreactivespring.handler.weather.WeatherHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.http.MediaType;


@Configuration
public class WeatherRouter {

  @RouterOperations({
      @RouterOperation(path = STREAM_WEATHER_END_POINT, beanClass = WeatherHandler.class, beanMethod = "streamWeather"),
      @RouterOperation(path = WEATHER_CITY_END_POINT, beanClass = WeatherHandler.class, beanMethod = "getCurrentWeather"),
  })
  @Bean
  public RouterFunction<ServerResponse> getWeatherStream(WeatherHandler weatherHandler) {

    return RouterFunctions
        .route(GET(STREAM_WEATHER_END_POINT).and(accept(MediaType.APPLICATION_JSON))
            , weatherHandler::streamWeather)
        .andRoute(GET(WEATHER_CITY_END_POINT).and(accept(MediaType.APPLICATION_JSON))
            , weatherHandler::getCurrentWeather);
  }

}
