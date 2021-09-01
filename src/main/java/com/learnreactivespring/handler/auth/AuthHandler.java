package com.learnreactivespring.handler.auth;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.learnreactivespring.model.AuthResponse;
import com.learnreactivespring.model.User;
import com.learnreactivespring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class AuthHandler {


  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  TokenProvider tokenProvider;


  public Mono signin(ServerRequest request) {
    Mono<User> loginRequest = request.bodyToMono(User.class);
    return loginRequest.flatMap(login -> userRepository.findById(login.getUsername())
        .flatMap(user -> {
          if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return ServerResponse.ok().contentType(APPLICATION_JSON).body(
                BodyInserters.fromValue(new AuthResponse(tokenProvider.generateToken(user))));
          } else {
            return ServerResponse.badRequest()
                .body(BodyInserters.fromValue(new String("Invalid credentials")));
          }
        }).switchIfEmpty(ServerResponse.badRequest()
            .body(BodyInserters.fromValue(new String("User does not exist")))));
  }


  public Mono signup(ServerRequest request) {
    Mono<User> userMono = request.bodyToMono(User.class);
    return userMono.map(user -> {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return user;
    }).flatMap(user -> userRepository.findById(user.getUsername())
        .flatMap(dbUser -> ServerResponse.badRequest()
            .body(BodyInserters.fromValue(new String("User already exist"))))
        .switchIfEmpty(saveUser(user).flatMap(
            savedUser -> ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(savedUser)))));
  }

  private Mono saveUser(User newUser) {

    return userRepository.save(newUser).map(
        user -> new AuthResponse(tokenProvider.generateToken(user))
    );

  }


}
