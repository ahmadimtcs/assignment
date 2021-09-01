package com.learnreactivespring.handler.auth;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

  @Autowired
  private TokenProvider tokenProvider;

  @Override
  @SuppressWarnings("unchecked")
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
    String username;
    try {
      username = tokenProvider.getUsernameFromToken(authToken);
    } catch (Exception e) {
      username = null;
    }
    if (username != null && !tokenProvider.isTokenExpired(authToken)) {
      Claims claims = tokenProvider.getAllClaimsFromToken(authToken);

      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,
          username, null);
      SecurityContextHolder.getContext()
          .setAuthentication(new AuthenticatedUser(username, null));
      return Mono.just(auth);
    } else {
      return Mono.empty();
    }
  }
}
