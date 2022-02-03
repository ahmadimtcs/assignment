package com.assignment.openweather.domain.mapper;


import com.assignment.openweather.domain.entity.CurrentEntity;
import com.assignment.openweather.domain.model.dto.CurrentDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrentMapper {

  CurrentDTO fromEntity(CurrentEntity currentEntity);

  CurrentEntity toEntity(CurrentDTO currentDTO);

}
