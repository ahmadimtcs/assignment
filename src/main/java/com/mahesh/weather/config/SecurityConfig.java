package com.mahesh.weather.config;

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
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(
      ServerHttpSecurity http) {
    return http.csrf().disable().authorizeExchange()
        .pathMatchers(HttpMethod.GET, "/swagger-ui/**", "/swagger-ui.html", "/webjars/**",
            "/swagger", "/webjars/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**",
            "/webjars/swagger-ui/index.html").permitAll()
        .pathMatchers("*").hasAuthority("USER")
        .anyExchange()
        .authenticated()
        .and().httpBasic()
        .and().build();
  }

  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    UserDetails user = User
        .withUsername("user")
        .password(encoder.encode("password"))
        .roles("USER")
        .build();
    return new MapReactiveUserDetailsService(user);
  }
}
