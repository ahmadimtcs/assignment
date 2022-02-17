package com.assignment.openweather.security;

import static java.util.Collections.singletonList;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

  private JwtTokenProvider tokenProvider;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
    String username = tokenProvider.getUsernameFromToken(authToken);
    return Mono.just(tokenProvider.validateToken(authToken))
        .filter(valid -> valid)
        .switchIfEmpty(Mono.empty())
        .map(valid -> {
          Claims claims = tokenProvider.getAllClaimsFromToken(authToken);
          String role = claims.get("role", String.class);
          return new UsernamePasswordAuthenticationToken(
              username,
              null,
              singletonList(new SimpleGrantedAuthority(role))
          );
        });
  }
}
