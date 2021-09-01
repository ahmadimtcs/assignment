package com.learnreactivespring.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.learnreactivespring.handler.city.CityHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class CityRouter {


  @RouterOperations({
      @RouterOperation(path = "/createcity", beanClass = CityHandler.class, beanMethod = "createCity"),
      @RouterOperation(path = "/createcities", beanClass = CityHandler.class, beanMethod = "createCities"),
      @RouterOperation(path = "/getallcities", beanClass = CityHandler.class, beanMethod = "getAllCities"),
      @RouterOperation(path = "/getcity/{cityName}", beanClass = CityHandler.class, beanMethod = "getCity"),
      @RouterOperation(path = "/updatecity/{cityName}", beanClass = CityHandler.class, beanMethod = "updateCity"),
      @RouterOperation(path = "/deletecity/{cityName}", beanClass = CityHandler.class, beanMethod = "deleteCity")})
  @Bean
  public RouterFunction<ServerResponse> cityRoute(CityHandler cityHandler) {

    return RouterFunctions
        .route(POST("/createcity").and(accept(APPLICATION_JSON)), cityHandler::createCity)
        .andRoute(POST("/createcities").and(accept(APPLICATION_JSON)),
            cityHandler::createCities)
        .andRoute(GET("/getallcities").and(accept(APPLICATION_JSON)),
            cityHandler::getAllCities)
        .andRoute(GET("/getcity/{cityName}").and(accept(APPLICATION_JSON)), cityHandler::getCity)
        .andRoute(PUT("/updatecity/{cityName}").and(accept(APPLICATION_JSON)),
            cityHandler::updateCity)
        .andRoute(DELETE("/deletecity/{cityName}").and(accept(APPLICATION_JSON)),
            cityHandler::deleteCity);
  }

}
