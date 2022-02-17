package com.assignment.openweather.routes;

import com.assignment.openweather.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class LoginRouter {

  @Bean
  public RouterFunction<ServerResponse> loginRoute(UserHandler userHandler) {
    return RouterFunctions
        .route(RequestPredicates.POST("/login")
        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
        .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), userHandler::login);
  }
}
