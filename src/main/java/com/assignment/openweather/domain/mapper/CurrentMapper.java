package com.assignment.openweather.domain.mapper;


import com.assignment.openweather.domain.entity.CurrentWeatherEntity;
import com.assignment.openweather.domain.model.dto.CurrentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrentMapper {

  CurrentDTO fromEntity(CurrentWeatherEntity currentEntity);

  CurrentWeatherEntity toEntity(CurrentDTO currentDTO);

}
