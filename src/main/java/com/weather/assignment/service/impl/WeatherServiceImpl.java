package com.weather.assignment.service.impl;

import com.weather.assignment.document.WeatherDetails;
import com.weather.assignment.dto.WeatherDetailsDto;
import com.weather.assignment.repository.WeatherRepository;
import com.weather.assignment.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.weather.assignment.constants.WeatherConstants.OPEN_WEATHER_URL;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

  private final WeatherRepository weatherRepository;

  private final ModelMapper modelMapper;

  @Autowired
  public WeatherServiceImpl(WeatherRepository weatherRepository, ModelMapper modelMapper) {
    super();
    this.weatherRepository = weatherRepository;
    this.modelMapper = modelMapper;
  }

  WebClient webClient = WebClient.create(OPEN_WEATHER_URL);

  @Value("${api.key}")
  private String apiKey;

  @Value(("${city.name}"))
  private String cityName;

  @Override
  public void getWeatherDetails() {
    log.info("In WeatherServiceImpl to retrieve the weather details ");
    webClient
        .get()
        .uri("/weather?q=" + cityName + "&appid=" + apiKey)
        .retrieve()
        .bodyToMono(WeatherDetails.class)
        .subscribe(s -> weatherRepository.save(s).subscribe());
  }

  @Override
  public Mono<WeatherDetailsDto> createWeatherDetails(Mono<WeatherDetailsDto> weatherDetailsDto) {
    log.info("Create and save the weather details");
    return weatherDetailsDto
        .map(m -> modelMapper.map(m, WeatherDetails.class))
        .flatMap(weatherRepository::save)
        .map(m -> modelMapper.map(m, WeatherDetailsDto.class));
  }

  @Override
  public Flux<WeatherDetailsDto> findAll() {
    log.info("Inside find all of weather service impl");
    return weatherRepository
        .findAll()
        .map(model -> modelMapper.map(model, WeatherDetailsDto.class));
  }

  @Override
  public Mono<Void> deleteWeatherDetailsById(Long id) {
    log.info("Deleted weather detail for id" + id);
    return weatherRepository.deleteById(id);
  }

  @Override
  public Mono<WeatherDetailsDto> updateWeatherDetails(
      Long id, Mono<WeatherDetailsDto> updatedWeather) {
    Mono<WeatherDetailsDto> weatherDetailsDto =
        weatherRepository
            .findById(id)
            .flatMap(
                data ->
                    updatedWeather
                        .map(m -> modelMapper.map(m, WeatherDetails.class))
                        .flatMap(weatherRepository::save)
                        .map(m -> modelMapper.map(m, WeatherDetailsDto.class)));
    log.info("Updated weather details for id" + id);
    return weatherDetailsDto;
  }
}
