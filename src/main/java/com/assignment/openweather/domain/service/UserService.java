package com.assignment.openweather.domain.service;

import com.assignment.openweather.domain.model.dto.UserDTO;
import reactor.core.publisher.Mono;

public interface UserService {

  Mono<UserDTO> createUser(UserDTO userDTO);

  Mono<UserDTO> get(String userName);

  void delete(String userName);
}
