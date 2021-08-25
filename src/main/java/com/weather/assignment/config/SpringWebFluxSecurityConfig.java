package com.weather.assignment.config;

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
public class SpringWebFluxSecurityConfig {

	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity httpSecurity) {
		httpSecurity.authorizeExchange().pathMatchers(HttpMethod.GET, "/api/**").permitAll()
				.pathMatchers(HttpMethod.DELETE).hasRole("ADMIN").anyExchange().authenticated().and().httpBasic()
				.and().formLogin();
		httpSecurity.csrf().disable();
		return httpSecurity.build();
	}

	@Bean
	MapReactiveUserDetailsService mapReactiveUserDetailsService() {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails user = User.withUsername("user").password(passwordEncoder.encode("test")).roles("USER").build();
		UserDetails user1 = User.withUsername("admin").password(passwordEncoder.encode("test")).roles("ADMIN").build();
		return new MapReactiveUserDetailsService(user, user1);
	}
}
