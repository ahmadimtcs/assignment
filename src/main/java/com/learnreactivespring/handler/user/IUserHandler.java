package com.learnreactivespring.handler.user;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IUserHandler {

  Mono<ServerResponse> getAllUsers(ServerRequest serverRequest);

  Mono<ServerResponse> getUserById(ServerRequest serverRequest);

  Mono<ServerResponse> deleteUser(ServerRequest serverRequest);


}
