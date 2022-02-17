package com.assignment.openweather.domain.mapper;

import com.assignment.openweather.domain.entity.UserEntity;
import com.assignment.openweather.domain.model.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserEntity toEntity(UserDTO userDTO);

  UserDTO fromEntity(UserEntity userEntity);

}
