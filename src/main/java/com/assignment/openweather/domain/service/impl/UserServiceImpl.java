package com.assignment.openweather.domain.service.impl;

import com.assignment.openweather.domain.entity.UserEntity;
import com.assignment.openweather.domain.mapper.UserMapper;
import com.assignment.openweather.domain.model.dto.UserDTO;
import com.assignment.openweather.domain.repository.UserRepository;
import com.assignment.openweather.domain.service.UserService;
import com.assignment.openweather.exception.ConflictException;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private UserMapper userMapper;

  @Override
  public Mono<UserDTO> createUser(UserDTO userDTO) {
    Mono<UserEntity> userEntity = userRepository.findByUsername(userDTO.getUsername());
    return userEntity.map(entity -> {
      if (entity != null) {
        throw new ConflictException("User is already registered with application");
      } else {
        return entity;
      }
    }).switchIfEmpty(create(userDTO)).map(userMapper::fromEntity);
  }

  @Override
  public Mono<UserDTO> get(String userName) {
    Mono<UserEntity> entityMono = userRepository.findByUsername(userName);
    return entityMono.map(userMapper::fromEntity);
  }

  @Override
  public void delete(String userName) {
    userRepository.deleteById(userName);
  }


  private Mono<UserEntity> create(UserDTO userDTO) {
    UserEntity entity = userMapper.toEntity(userDTO);
    entity.setUserId(UUID.randomUUID().toString());
    entity.setRoleCode("user");
    entity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    return userRepository.save(entity);
  }
}
