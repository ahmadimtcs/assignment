package com.learnreactivespring.handler.user;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import com.learnreactivespring.model.City;
import com.learnreactivespring.model.User;
import com.learnreactivespring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler implements IUserHandler {

  @Autowired
  UserRepository userRepository;

  static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

  public Mono<ServerResponse> updateUser(ServerRequest request) {

    String userName = request.pathVariable("username");
    Mono<User> userRequest = request.bodyToMono(User.class);
    Mono<User> userMono = userRepository.findById(userName);

    return userMono.flatMap(user1 ->
        userRepository.deleteById(user1.getUsername())
            .then(userRequest.flatMap(newUser -> userRepository.save(newUser))
                .then(ServerResponse.ok().contentType(APPLICATION_JSON)
                    .body(fromValue("user updated"))))
    ).switchIfEmpty(notFound);
  }


  public Mono<ServerResponse> getUserById(ServerRequest request) {

    String username = request.pathVariable("username");
    Mono<User> userMono = userRepository.findById(username);

    return userMono.flatMap(
            user ->
                ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(user))
        )
        .switchIfEmpty(notFound).log();
  }


  public Mono deleteUser(ServerRequest serverRequest) {

    String username = serverRequest.pathVariable("username");
    return userRepository.findById(username).flatMap(
        user -> userRepository.deleteById(user.getUsername())
            .then(ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(fromValue(user.getUsername() + " removed")))
    ).switchIfEmpty(notFound);

  }

  public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(userRepository.findAll(),
            User.class);
  }


}
