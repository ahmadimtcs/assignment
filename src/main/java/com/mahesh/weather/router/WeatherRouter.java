package com.mahesh.weather.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;

import com.mahesh.weather.handler.UserCityHandler;
import com.mahesh.weather.handler.WeatherHandler;
import com.mahesh.weather.model.City;
import com.mahesh.weather.model.UserCityDetails;
import com.mahesh.weather.model.WeatherDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class WeatherRouter {

  private UserCityHandler userCityHandler;

  private WeatherHandler weatherHandler;

  WeatherRouter(UserCityHandler userCityHandler, WeatherHandler weatherHandler) {
    this.userCityHandler = userCityHandler;
    this.weatherHandler = weatherHandler;
  }

  @RouterOperations(value = {
      @RouterOperation(path = "/v1/city", operation = @Operation(operationId = "findCity", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = City.class))),
          responses = @ApiResponse(content = @Content(schema = @Schema(implementation = City.class))))
          , method = RequestMethod.POST, beanClass = UserCityHandler.class, beanMethod = "findCity"),
      @RouterOperation(path = "/v1/userCity", operation = @Operation(operationId = "addUserCity", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserCityDetails.class))),
          responses = @ApiResponse(content = @Content(schema = @Schema(implementation = UserCityDetails.class)))),
          method = RequestMethod.POST, beanClass = UserCityHandler.class, beanMethod = "addUserCity"),
      @RouterOperation(path = "/v1/userCities", operation = @Operation(operationId = "addUserCities",
          requestBody = @RequestBody(required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = City.class)))),
          responses = @ApiResponse(content = @Content(schema = @Schema(implementation = UserCityDetails.class)))),
          method = RequestMethod.POST, beanClass = UserCityHandler.class, beanMethod = "addUserCities"),
      @RouterOperation(path = "/v1/userCities/flux", operation = @Operation(operationId = "addUserCitiesFlux",
          requestBody = @RequestBody(required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = City.class)))),
          responses = @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = City.class))))),
          method = RequestMethod.POST, beanClass = UserCityHandler.class, beanMethod = "addUserCitiesFlux"),
      @RouterOperation(path = "/v1/userCities", operation = @Operation(operationId = "updateUserCities",
          requestBody = @RequestBody(required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = City.class)))),
          responses = @ApiResponse(content = @Content(schema = @Schema(implementation = UserCityDetails.class)))),
          method = RequestMethod.PUT, beanClass = UserCityHandler.class, beanMethod = "updateUserCities"),
      @RouterOperation(path = "/v1/userCities", operation = @Operation(operationId = "deleteUserCities",
          responses = @ApiResponse(responseCode = "200", description = "Ok")),
          method = RequestMethod.DELETE, beanClass = UserCityHandler.class, beanMethod = "deleteUserCities"),
      @RouterOperation(path = "/v1/userCities", operation = @Operation(operationId = "findAllUserCities",
          responses = @ApiResponse(content = @Content(schema = @Schema(implementation = UserCityDetails.class)))),
          method = RequestMethod.GET, beanClass = UserCityHandler.class, beanMethod = "findAllUserCities"),
      @RouterOperation(path = "/v1/userCities/{userId}", operation = @Operation(operationId = "removeAllCitiesForUser",
          parameters = {
              @Parameter(in = ParameterIn.PATH, name = "userId", description = "User Id", required = true)},
          responses = @ApiResponse(content = @Content(schema = @Schema(implementation = UserCityDetails.class)))),
          method = RequestMethod.DELETE, beanClass = UserCityHandler.class, beanMethod = "removeAllCitiesForUser"),
      @RouterOperation(path = "/v1/weatherCache", operation = @Operation(operationId = "getWeatherOfAllUserCitiesWithCache",
          responses = @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = WeatherDetails.class))))),
          method = RequestMethod.GET, beanClass = WeatherHandler.class, beanMethod = "getWeatherOfAllUserCitiesWithCache"),
      @RouterOperation(path = "/v1/weatherCache/city", operation = @Operation(operationId = "getWeatherForCityWithCache",
          requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = City.class))),
          responses = @ApiResponse(content = @Content(schema = @Schema(implementation = WeatherDetails.class)))),
          method = RequestMethod.POST, beanClass = WeatherHandler.class, beanMethod = "getWeatherForCityWithCache")
  })
  @Bean
  RouterFunction<ServerResponse> weatherRoutes() {
    return RouterFunctions.route()
        .POST("/v1/city", request -> userCityHandler.findCity(request))
        .nest(path("/v1/userCities"), builder -> builder
            .POST("", request -> userCityHandler.addUserCities(request))
            .POST("/flux", request -> userCityHandler.addUserCitiesFlux(request))
            .PUT("", request -> userCityHandler.updateUserCities(request))
            .DELETE("", request -> userCityHandler.deleteUserCities())
            .GET("", request -> userCityHandler.findAllUserCities(request))
            .DELETE("/{userId}", request -> userCityHandler.removeAllCitiesForUser(request))
            .build())
        /*.nest(path("/v1/weather"), builder -> builder
            .GET("", request -> weatherHandler.getWeatherOfAllUserCities())
            .POST("/city", request -> weatherHandler.getWeatherForCity(request))
            .build())*/
        .nest(path("/v1/weatherCache"), builder -> builder
            .GET("", request -> weatherHandler.getWeatherOfAllUserCitiesWithCache())
            .POST("/city", request -> weatherHandler.getWeatherForCityWithCache(request))
            .build())
        .POST("/v1/userCity", request -> userCityHandler.addUserCity(request))
        .build();
  }

}
