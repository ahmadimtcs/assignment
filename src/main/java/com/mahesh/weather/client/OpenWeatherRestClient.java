package com.mahesh.weather.client;

import com.mahesh.weather.model.City;
import com.mahesh.weather.model.WeatherDetails;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OpenWeatherRestClient {

  @Autowired
  WebClient webClient;

  @Value(value = "${key.geolocation-url}")
  private String GEOLOCATION_URL;

  @Value(value = "${key.openweather-url}")
  private String OPENWEATHER_URL;

  @Value(value = "${key.api-key}")
  private String API_KEY;

  public Mono<City> findCityByName(City city) {
    var params = new StringBuilder().append(city.getName());
    if (StringUtils.isNotBlank(city.getCountry())) {
      params.append(",").append(city.getCountry());
    }

    var uri = UriComponentsBuilder.fromUriString(GEOLOCATION_URL)
        .queryParam("q", params.toString())
        .queryParam("limit", 1)
        .queryParam("appid", API_KEY)
        .buildAndExpand().toUri();

    var response = webClient
        .get()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(
            new ParameterizedTypeReference<List<City>>() {
            }))
        .flatMap(
            cities -> CollectionUtils.isNotEmpty(cities) ? Mono.just(cities.get(0)) : Mono.empty())
        .log();

    log.info("response :=> {}", response);
    return response;
  }

  public Mono<WeatherDetails> getWeatherOfCity(City city) {
    var uri = UriComponentsBuilder.fromUriString(OPENWEATHER_URL)
        .queryParam("lat", city.getLat())
        .queryParam("lon", city.getLon())
        .queryParam("units", "metric")
        .queryParam("appid", API_KEY)
        .buildAndExpand().toUri();
    var response = webClient
        .get()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(WeatherDetails.class))
        .switchIfEmpty(Mono.empty())
        .log();
    return response;
  }
}
