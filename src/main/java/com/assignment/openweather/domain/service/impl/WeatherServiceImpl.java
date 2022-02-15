package com.assignment.openweather.domain.service.impl;

import static java.lang.String.valueOf;

import com.assignment.openweather.client.OpenAPIServiceRestClient;
import com.assignment.openweather.domain.entity.LocationEntity;
import com.assignment.openweather.domain.mapper.CurrentMapper;
import com.assignment.openweather.domain.mapper.LocationMapper;
import com.assignment.openweather.domain.model.dto.LocationDataResponseDTO;
import com.assignment.openweather.domain.model.dto.SearchDataResponseDTO;
import com.assignment.openweather.domain.repository.LocationRepository;
import com.assignment.openweather.domain.service.WeatherService;
import com.assignment.openweather.exception.ResourceNotFoundException;
import com.assignment.openweather.rest.model.LocationDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WeatherServiceImpl implements WeatherService {

  private final LocationRepository weatherInfoRepository;

  private final OpenAPIServiceRestClient openAPIServiceRestClient;

  private final CurrentMapper currentMapper;

  private final LocationMapper weatherMapper;

  @Autowired
  public WeatherServiceImpl(
      LocationRepository weatherInfoRepository,
      OpenAPIServiceRestClient openAPIServiceRestClient,
      CurrentMapper currentMapper,
      LocationMapper weatherMapper) {
    this.weatherInfoRepository = weatherInfoRepository;
    this.openAPIServiceRestClient = openAPIServiceRestClient;
    this.currentMapper = currentMapper;
    this.weatherMapper = weatherMapper;
  }

  @Override
  @Transactional
  public Mono<List<String>> persist(LocationDTO locationDTO) {
    if (!CollectionUtils.isEmpty(locationDTO.getLocations())) {
      Flux<LocationEntity> weatherFlux = getWeatherEntityFlux(locationDTO);
      return weatherFlux.map(LocationEntity::getLocationName).collect(Collectors.toList());
    }
    return Mono.empty();
  }

  @Override
  public Mono<LocationDataResponseDTO> get(String locationName) {
    if (!ObjectUtils.isEmpty(locationName)) {
      Optional<Mono<LocationEntity>> entityMono = weatherInfoRepository.findByLocationName(
          locationName);
      if (entityMono.isPresent()) {
        return entityMono.get().map(weatherMapper::fromEntity);
      } else {
        Flux<LocationEntity> locationEntityFlux = getWeatherEntityFlux(
            new LocationDTO(Collections.singletonList(locationName)));
        return locationEntityFlux.map(weatherMapper::fromEntity).single();
      }
    }
    return Mono.empty();
  }

  @Override
  public Flux<LocationDataResponseDTO> get() {
    Flux<LocationEntity> weatherEntityFlux = weatherInfoRepository.findAll()
        .switchIfEmpty(Flux.empty());
    return weatherEntityFlux.map(weatherMapper::fromEntity);
  }

  @Override
  public void delete(String locationName) {
    if (!ObjectUtils.isEmpty(locationName)) {
      Optional<Mono<LocationEntity>> entityMono = weatherInfoRepository.findByLocationName(locationName);
      if (entityMono.isPresent()) {
        Optional<String> locationId = entityMono.get().map(LocationEntity::getLocationId).blockOptional();
        locationId.map(weatherInfoRepository::deleteById);
      }else{
        throw new ResourceNotFoundException("The required resource is not found for the given location name");
      }
    }
  }

  private Flux<LocationEntity> getWeatherEntityFlux(LocationDTO locationDTO) {
    List<SearchDataResponseDTO> searchDataResponseDTOS = new ArrayList<>();
    locationDTO.getLocations().forEach(location -> {
      Mono<SearchDataResponseDTO> searchDataResponseDTOMono = openAPIServiceRestClient.searchData(
          location);
      searchDataResponseDTOS.add(searchDataResponseDTOMono.block());
    });
    List<LocationEntity> locationEntities = new ArrayList<>();
    searchDataResponseDTOS.forEach(searchDataResponseDTO -> {
      LocationEntity locationEntity = new LocationEntity();
      locationEntity.setCreatedDate(LocalDate.now());
      locationEntity.setModifiedDate(LocalDate.now());
      locationEntity.setLatitude(searchDataResponseDTO.getList().get(0).getCoordinates().getLat());
      locationEntity.setLongitude(searchDataResponseDTO.getList().get(0).getCoordinates().getLon());
      Double latitude = searchDataResponseDTO.getList().get(0).getCoordinates().getLat();
      Double longitude = searchDataResponseDTO.getList().get(0).getCoordinates().getLon();
      Mono<LocationDataResponseDTO> weatherDataResponseDTO = openAPIServiceRestClient.getWeatherData(
          valueOf(latitude), valueOf(longitude));
      Optional<LocationDataResponseDTO> dataResponseDTO = weatherDataResponseDTO.blockOptional();
      dataResponseDTO.ifPresent(
          responseDTO -> locationEntity.setCurrent(
              currentMapper.toEntity(responseDTO.getCurrent())));
      locationEntities.add(locationEntity);
    });

    return weatherInfoRepository.saveAll(locationEntities);
  }
}
