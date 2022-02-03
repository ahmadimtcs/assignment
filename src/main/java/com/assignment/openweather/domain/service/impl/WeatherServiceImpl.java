package com.assignment.openweather.domain.service.impl;

import static java.lang.String.valueOf;

import com.assignment.openweather.client.OpenAPIServiceRestClient;
import com.assignment.openweather.domain.entity.WeatherEntity;
import com.assignment.openweather.domain.mapper.CurrentMapper;
import com.assignment.openweather.domain.mapper.WeatherMapper;
import com.assignment.openweather.domain.model.dto.SearchDataResponseDTO;
import com.assignment.openweather.domain.model.dto.WeatherDataResponseDTO;
import com.assignment.openweather.domain.repository.WeatherInfoRepository;
import com.assignment.openweather.domain.service.WeatherService;
import com.assignment.openweather.rest.model.LocationDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WeatherServiceImpl implements WeatherService {

  @Autowired
  private WeatherInfoRepository weatherInfoRepository;

  @Autowired
  private OpenAPIServiceRestClient openAPIServiceRestClient;

  @Autowired
  private CurrentMapper currentMapper;

  @Autowired
  private WeatherMapper weatherMapper;


  @Override
  @Transactional
  public Mono<List<String>> persist(LocationDTO locationDTO) {
    if (!CollectionUtils.isEmpty(locationDTO.getLocations())) {
      Flux<WeatherEntity> weatherFlux = getWeatherEntityFlux(locationDTO);
      return weatherFlux.map(WeatherEntity::getLocationName).collect(Collectors.toList());
    }
    return Mono.empty();
  }

  @Override
  public Mono<WeatherDataResponseDTO> get(String locationName) {
//    Optional.ofNullable(locationName).ifPresent(location -> {
//      weatherInfoRepository.findByLocationName(location).orElse(()->{
//        getWeatherEntityFlux(new LocationDTO().setLocations(Arrays.asList(locationName)))
//      });
//    });
    return null;
  }

  private Flux<WeatherEntity> getWeatherEntityFlux(LocationDTO locationDTO) {
    List<SearchDataResponseDTO> searchDataResponseDTOS = new ArrayList<>();
    locationDTO.getLocations().forEach(location -> {
      Mono<SearchDataResponseDTO> searchDataResponseDTOMono = openAPIServiceRestClient.searchData(
          location);
      searchDataResponseDTOS.add(searchDataResponseDTOMono.block());
    });
    List<WeatherEntity> weatherEntities = new ArrayList<>();
    searchDataResponseDTOS.forEach(searchDataResponseDTO -> {
      WeatherEntity weatherEntity = new WeatherEntity();
      weatherEntity.setCreatedDate(LocalDate.now());
      weatherEntity.setModifiedDate(LocalDate.now());
      weatherEntity.setLatitude(searchDataResponseDTO.getList().get(0).getCoordinates().getLat());
      weatherEntity.setLongitude(searchDataResponseDTO.getList().get(0).getCoordinates().getLon());
      Double latitude = searchDataResponseDTO.getList().get(0).getCoordinates().getLat();
      Double longitude = searchDataResponseDTO.getList().get(0).getCoordinates().getLon();
      Mono<WeatherDataResponseDTO> weatherDataResponseDTO = openAPIServiceRestClient.getWeatherData(
          valueOf(latitude), valueOf(longitude));
      Optional<WeatherDataResponseDTO> dataResponseDTO = weatherDataResponseDTO.blockOptional();
      dataResponseDTO.ifPresent(
          responseDTO -> weatherEntity.setCurrent(
              currentMapper.toEntity(responseDTO.getCurrent())));
      weatherEntities.add(weatherEntity);
    });

    return weatherInfoRepository.saveAll(weatherEntities);
  }
}
