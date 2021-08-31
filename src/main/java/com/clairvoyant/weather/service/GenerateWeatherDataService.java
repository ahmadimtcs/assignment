package com.clairvoyant.weather.service;

import com.clairvoyant.weather.config.WeatherProperties;
import com.clairvoyant.weather.document.WeatherDetails;
import com.clairvoyant.weather.dto.WeatherResponse;
import com.clairvoyant.weather.repository.WeatherRepository;
import java.time.Duration;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 19-08-2021 15:27
 */
@Service
@Slf4j
public class GenerateWeatherDataService {

  private final WeatherRepository weatherRepository;
  private final WeatherProperties weatherProperties;
  private final WebClient webClient;

  @Autowired
  public GenerateWeatherDataService(WeatherRepository weatherRepository,
      WeatherProperties weatherProperties) {
    super();
    this.weatherRepository = weatherRepository;
    this.weatherProperties = weatherProperties;
    this.webClient = WebClient.create(weatherProperties.getUrl());
  }

  Instant start = Instant.now();

  public void refreshAfterTime() {

    Instant end = Instant.now();
    Duration timeElapsed = Duration.between(start, end);
    if (timeElapsed.toMinutes() >= weatherProperties.getMinutes()) {
      refreshData();
      start = Instant.now();
    }

  }

  public void refreshData() {
    log.info("refresh Data Invoked");
    webClient
        .get().uri("/find?lat=" + weatherProperties.getLat() + "&lon=" + weatherProperties.getLon()
        + "&cnt=10&appid=" + weatherProperties.getKey())
        .retrieve().bodyToMono(WeatherResponse.class).subscribe(response -> {

      if (response.getCod().equals("200")) {
        response.getList().forEach(item -> {
          WeatherDetails weatherDetails = new WeatherDetails();
          weatherDetails.setId(item.getId());
          weatherDetails.setName(item.getName());
          if (item.getMain() != null) {
            weatherDetails.setTemp(item.getMain().getTemp());
            weatherDetails.setFeelsLike(item.getMain().getFeelsLike());
          }
          weatherRepository.save(weatherDetails).subscribe();
        });
      }
    });
    start = Instant.now();
  }

}
