package com.assignment.openweather.domain.mapper;

import com.assignment.openweather.domain.entity.LocationEntity;
import com.assignment.openweather.domain.model.dto.WeatherDataResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
    CurrentMapper.class
})
public interface WeatherMapper {

  WeatherDataResponseDTO fromEntity(LocationEntity weatherEntity);

}