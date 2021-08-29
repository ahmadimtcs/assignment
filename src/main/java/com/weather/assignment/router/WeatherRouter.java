package com.weather.assignment.router;

import com.weather.assignment.handler.WeatherHandler;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.weather.assignment.constants.WeatherConstants.WEATHER_URL;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class WeatherRouter {

  @Bean
  public RouterFunction<ServerResponse> getWeatherDetailsByCity(WeatherHandler handler) {
    return RouterFunctions.route(
            GET(WEATHER_URL).and(accept(MediaType.APPLICATION_JSON)), handler::getWeatherDetails)
        .andRoute(
            DELETE(WEATHER_URL + "/{id}").and(accept(MediaType.APPLICATION_JSON)),
            handler::deleteWeatherDetails)
        .andRoute(
            POST(WEATHER_URL).and(accept(MediaType.APPLICATION_JSON)),
            handler::createWeatherDetails)
        .andRoute(
            PUT(WEATHER_URL + "/{id}").and(accept(MediaType.APPLICATION_JSON)),
            handler::updateWeatherDetails);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
