package com.clairvoyant.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
public class ReactiveSecurity {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

    http.csrf().disable()
        .authorizeExchange()
            .pathMatchers(HttpMethod.GET).permitAll()
            .pathMatchers(HttpMethod.PUT).permitAll()
            .pathMatchers(HttpMethod.DELETE).hasRole("ADMIN")
            .anyExchange()
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .formLogin();
    return http.build();
  }

  @Bean
  public MapReactiveUserDetailsService userService() {

    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserDetails user1 = User.withUsername("user")
        .password(encoder.encode("user"))
        .roles("USER")
        .build();

    UserDetails user2 = User.withUsername("admin")
        .password(encoder.encode("admin"))
        .roles("ADMIN")
        .build();

    return new MapReactiveUserDetailsService(user1, user2);
  }

}
