package com.mahesh.weather.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HelloRouter {


  @Bean
  RouterFunction<ServerResponse> helloRoutes() {
    return RouterFunctions.route()
        .GET("/hello", request -> ServerResponse.ok().bodyValue("Hello World!!!").log())
        .build();
  }

}
