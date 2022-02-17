package com.assignment.openweather.domain.service.impl;

import com.assignment.openweather.domain.entity.UserEntity;
import com.assignment.openweather.domain.mapper.UserMapper;
import com.assignment.openweather.domain.model.dto.UserDTO;
import com.assignment.openweather.domain.repository.UserRepository;
import com.assignment.openweather.domain.service.LoginService;
import com.assignment.openweather.exception.ResourceNotFoundException;
import com.assignment.openweather.exception.UnAuthorizedException;
import com.assignment.openweather.security.AuthenticatedUser;
import com.assignment.openweather.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder;

  private JwtTokenProvider tokenProvider;

  private UserMapper userMapper;

  @Override
  public Mono<AuthenticatedUser> authenticate(UserDTO userDTO) {
    Mono<UserEntity> userEntityMono = userRepository.findByUsername(userDTO.getUsername());
    Mono<AuthenticatedUser> authenticatedUserMono = userEntityMono.map(userEntity -> {
      if (userEntity != null) {
        if (passwordEncoder.matches(userDTO.getPassword(), userEntity.getPassword())) {
          return userEntity;
        } else {
          throw new UnAuthorizedException("The user is not authorized in the system");
        }
      } else {
        throw new ResourceNotFoundException("The user is not found in the system");
      }
    }).map(this::getToken);
    return authenticatedUserMono;
  }

  private AuthenticatedUser getToken(UserEntity userEntity) {
    String token = tokenProvider.generateToken(userMapper.fromEntity(userEntity));
    AuthenticatedUser authenticatedUser = new AuthenticatedUser();
    authenticatedUser.setUsername(userEntity.getUsername());
    authenticatedUser.setToken(token);
    return authenticatedUser;
  }
}
