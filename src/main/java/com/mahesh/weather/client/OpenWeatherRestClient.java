package com.mahesh.weather.client;

import com.mahesh.weather.model.City;
import com.mahesh.weather.model.WeatherDetails;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

  private WebClient webClient;

  private String GEOLOCATION_URL;

  private String OPENWEATHER_URL;

  private String API_KEY;

  OpenWeatherRestClient(WebClient webClient,
      @Value(value = "${key.geolocation-url}") String geoLocationURL,
      @Value(value = "${key.openweather-url}") String openWeatherURL,
      @Value(value = "${key.api-key}") String apiKey) {
    this.webClient = webClient;
    this.GEOLOCATION_URL = geoLocationURL;
    this.OPENWEATHER_URL = openWeatherURL;
    this.API_KEY = apiKey;
  }

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
        .switchIfEmpty(Mono.defer(() -> Mono.empty()))
        .log();
    return response;
  }
}
