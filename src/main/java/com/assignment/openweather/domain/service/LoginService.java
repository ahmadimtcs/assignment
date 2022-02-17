package com.assignment.openweather.domain.service;

import com.assignment.openweather.domain.model.dto.UserDTO;
import com.assignment.openweather.security.AuthenticatedUser;
import reactor.core.publisher.Mono;

public interface LoginService {

  Mono<AuthenticatedUser> authenticate(UserDTO userDTO);

}
