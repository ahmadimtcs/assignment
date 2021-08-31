package com.clairvoyant.weather.router;

import static com.clairvoyant.weather.contants.WeatherConstants.WEATHER_FUNCTIONAL_END_POINT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.clairvoyant.weather.dto.WeatherDto;
import com.clairvoyant.weather.handler.WeatherHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 23-08-2021 12:26
 */
@Configuration
@Slf4j
public class WeatherRouter {


  @RouterOperations(value = {
      @RouterOperation(path = WEATHER_FUNCTIONAL_END_POINT,
          operation = @Operation(operationId = "getAllWeatherDetails", responses = @ApiResponse(content = @Content(schema = @Schema(implementation = WeatherDto.class))))
          , method = RequestMethod.GET, beanClass = WeatherHandler.class, beanMethod = "getAllWeatherDetails"),
      @RouterOperation(path = WEATHER_FUNCTIONAL_END_POINT, operation = @Operation(operationId = "createWeatherDetails", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = WeatherDto.class))), responses = @ApiResponse(content = @Content(schema = @Schema(implementation = WeatherDto.class))))
          , method = RequestMethod.POST, beanClass = WeatherHandler.class, beanMethod = "createWeatherDetails"),
      @RouterOperation(path = WEATHER_FUNCTIONAL_END_POINT
          + "/{id}", beanClass = WeatherHandler.class, operation = @Operation(operationId = "updateWeatherDetails", parameters = {
          @Parameter(in = ParameterIn.PATH, name = "id", description = "Id", required = true)}, requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = WeatherDto.class))), responses = @ApiResponse(content = @Content(schema = @Schema(implementation = WeatherDto.class))))
          , method = RequestMethod.PUT, beanMethod = "updateWeatherDetails"),
      @RouterOperation(path = WEATHER_FUNCTIONAL_END_POINT
          + "/{id}", beanClass = WeatherHandler.class,
          operation = @Operation(operationId = "deleteWeatherDetailsById", parameters = {
              @Parameter(in = ParameterIn.PATH, name = "id", description = "Id", required = true)}, responses = @ApiResponse(content = @Content(schema = @Schema(implementation = WeatherDto.class))))
          , method = RequestMethod.DELETE, beanMethod = "deleteWeatherDetailsById"),
      @RouterOperation(path = WEATHER_FUNCTIONAL_END_POINT
          + "/city/{city}", beanClass = WeatherHandler.class, operation = @Operation(operationId = "getWeatherDetailsByCity", parameters = {
          @Parameter(in = ParameterIn.PATH, name = "city", description = "City", required = true)}, responses = @ApiResponse(content = @Content(schema = @Schema(implementation = WeatherDto.class))))
          , method = RequestMethod.GET, beanMethod = "getWeatherDetailsByCity")})
  @Bean
  public RouterFunction<ServerResponse> weatherRoute(WeatherHandler weatherHandler) {

    return RouterFunctions
        .route(GET(WEATHER_FUNCTIONAL_END_POINT).and(accept(APPLICATION_JSON)),
            serverRequest -> weatherHandler.getAllWeatherDetails())
        .andRoute(POST(WEATHER_FUNCTIONAL_END_POINT).and(accept(APPLICATION_JSON)),
            weatherHandler::createWeatherDetails)
        .andRoute(PUT(WEATHER_FUNCTIONAL_END_POINT + "/{id}").and(accept(APPLICATION_JSON)),
            weatherHandler::updateWeatherDetails)
        .andRoute(DELETE(WEATHER_FUNCTIONAL_END_POINT + "/{id}").and(accept(APPLICATION_JSON)),
            weatherHandler::deleteWeatherDetailsById)
        .andRoute(GET(WEATHER_FUNCTIONAL_END_POINT + "/city/{city}").and(accept(APPLICATION_JSON)),
            weatherHandler::getWeatherDetailsByCity);
  }
}
