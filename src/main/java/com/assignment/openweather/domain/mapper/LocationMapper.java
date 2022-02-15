package com.assignment.openweather.domain.mapper;

import com.assignment.openweather.domain.entity.LocationEntity;
import com.assignment.openweather.domain.model.dto.LocationDataResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
    CurrentMapper.class
})
public interface LocationMapper {

  LocationDataResponseDTO fromEntity(LocationEntity locationEntity);

}