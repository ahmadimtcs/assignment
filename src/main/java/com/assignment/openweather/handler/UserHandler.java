package com.assignment.openweather.handler;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

import com.assignment.openweather.domain.model.dto.UserDTO;
import com.assignment.openweather.domain.service.LoginService;
import com.assignment.openweather.domain.service.UserService;
import com.assignment.openweather.security.AuthenticatedUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UserHandler {

  private UserService userService;

  private LoginService loginService;


  public Mono<ServerResponse> persist(ServerRequest serverRequest) {
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    Mono<UserDTO> userDTOMono = serverRequest.bodyToMono(UserDTO.class)
        .flatMap(userService::createUser);
    return ServerResponse.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
        .body(userDTOMono, UserDTO.class).switchIfEmpty(notFound);

  }

  public Mono<ServerResponse> login(ServerRequest serverRequest) {
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    Mono<AuthenticatedUser> authenticatedUserMono = serverRequest.bodyToMono(UserDTO.class)
        .flatMap(loginService::authenticate);
    return ServerResponse.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON)
        .body(authenticatedUserMono, AuthenticatedUser.class).switchIfEmpty(notFound);
  }
}
