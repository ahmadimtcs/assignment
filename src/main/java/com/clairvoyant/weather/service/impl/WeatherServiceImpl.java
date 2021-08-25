package com.clairvoyant.weather.service.impl;

import com.clairvoyant.weather.document.WeatherDetails;
import com.clairvoyant.weather.dto.WeatherDto;
import com.clairvoyant.weather.repository.WeatherRepository;
import com.clairvoyant.weather.service.GenerateWeatherDataService;
import com.clairvoyant.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 20-08-2021 17:37
 */
@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

  private final WeatherRepository weatherRepository;
  private final ModelMapper modelMapper;
  private final GenerateWeatherDataService generateWeatherDataService;

  @Autowired
  public WeatherServiceImpl(WeatherRepository weatherRepository, ModelMapper modelMapper,
      GenerateWeatherDataService generateWeatherDataService) {
    super();
    this.weatherRepository = weatherRepository;
    this.modelMapper = modelMapper;
    this.generateWeatherDataService = generateWeatherDataService;
  }

  @Override
  public Flux<WeatherDto> findAll() {
    log.info("Inside Class WeatherServiceImpl Method findAll");
    generateWeatherDataService.refreshAfterTime();
    return weatherRepository.findAll().map(m -> modelMapper.map(m, WeatherDto.class));
  }

  @Override
  public Mono<WeatherDto> createWeatherDetails(Mono<WeatherDto> weatherDto) {
    log.info("Inside Class WeatherServiceImpl Method createWeatherDetails");
    generateWeatherDataService.refreshAfterTime();
    return weatherDto.map(m -> modelMapper.map(m, WeatherDetails.class))
        .flatMap(weatherRepository::save)
        .map(m -> modelMapper.map(m, WeatherDto.class));
  }

  @Override
  public Mono<WeatherDto> updateWeatherDetails(Long id, Mono<WeatherDto> weatherDto) {
    log.info("Inside Class WeatherServiceImpl Method updateWeatherDetails");
    generateWeatherDataService.refreshAfterTime();
    return weatherRepository.findById(id)
        .flatMap(result ->
            weatherDto.map(m -> modelMapper.map(m, WeatherDetails.class))
                .flatMap(weatherRepository::save)
                .map(m -> modelMapper.map(m, WeatherDto.class))
        );
  }

  @Override
  public Mono<WeatherDto> getWeatherDetailsByCity(String city) {
    log.info("Inside Class WeatherServiceImpl Method getWeatherDetailsByCity");
    generateWeatherDataService.refreshAfterTime();
    return weatherRepository.findByName(city).map(m -> modelMapper.map(m, WeatherDto.class));

  }

  @Override
  public Mono<Void> deleteWeatherDetailsById(Long id) {
    log.info("Inside Class WeatherServiceImpl Method deleteWeatherDetailsById");
    generateWeatherDataService.refreshAfterTime();
    return weatherRepository.deleteById(id);
  }
}
