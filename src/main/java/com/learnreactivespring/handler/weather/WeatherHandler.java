package com.learnreactivespring.handler.weather;

import com.learnreactivespring.model.Weather;

import com.learnreactivespring.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class WeatherHandler implements IWeatherHandler {

  @Autowired
  WeatherRepository weatherRepository;


  private WebClient webClient;

  @Value("${weather.api.url}")
  private String weatherApiURL;

  @Value("${weather.api.key}")
  private String weatherApiKey;

  public Mono<ServerResponse> create(Weather weather) {
    return this.createWeather(weather).flatMap(weather1 -> ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(weather1, Weather.class)).log();
  }

  public Mono<ServerResponse> streamWeather(ServerRequest serverRequest) {

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_STREAM_JSON)
        .body(weatherRepository.findWeatherBy(), Weather.class).log();
  }

  public Mono<ServerResponse> getCurrentWeather(ServerRequest request) {

    String cityName = request.pathVariable("cityName");

    Mono<Weather> weather = this.getZipCodeWeatherDB(cityName)
        .switchIfEmpty(this.getCurrentWeatherOpenApi(cityName));

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(weather, Weather.class).log();
  }

  public Mono<Weather> getCurrentWeatherOpenApi(String cityName) {

    this.webClient = WebClient.create(this.weatherApiURL);
    String URI = "?q=" + cityName + "&appid=" + weatherApiKey;

    Mono<Weather> weatherMonoData =
        this.webClient.get().uri(URI).retrieve().bodyToMono(Weather.class).log();

    return weatherMonoData
        .map(
            s -> {
              s.setName(cityName);
              return s;
            })
        .flatMap(this::createWeather)
        .log();
  }

  public Mono<Weather> getZipCodeWeatherDB(String cityName) {
    return weatherRepository.findById(cityName).log();
  }

  public Mono<Weather> createWeather(Weather weather) {
    return weatherRepository.save(weather).log();
  }


}
