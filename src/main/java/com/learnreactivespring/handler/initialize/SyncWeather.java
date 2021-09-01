package com.learnreactivespring.handler.initialize;

import com.learnreactivespring.model.City;
import com.learnreactivespring.model.User;
import com.learnreactivespring.model.Weather;
import com.learnreactivespring.handler.city.CityHandler;
import com.learnreactivespring.handler.weather.WeatherHandler;
import com.learnreactivespring.repository.CityRepository;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SyncWeather implements CommandLineRunner {

  @Autowired
  MongoOperations mongoOperations;

  @Autowired
  CityHandler CityHandler;

  @Autowired
  WeatherHandler weatherWebService;


  @Autowired
  CityRepository cityRepository;

  @Override
  public void run(String... args) throws Exception {

    createCappedWeatherCollection();
    initializeWeatherData();
    hourlyWeatherSync();
  }


  private void createCappedWeatherCollection() {

    mongoOperations.dropCollection(Weather.class);
    log.info("dropped weather collection");

    mongoOperations.createCollection(Weather.class,
        CollectionOptions.empty().maxDocuments(50).size(50000).capped());
    log.info("create capped weather collection");

  }

  private void initializeWeatherData() {
    log.info("Initialize tracked weather data");
    processWeatherData().subscribe();
  }

  public void hourlyWeatherSync() {
    log.info("Initialize hourly weather tracker");
    Flux.interval(Duration.ofMinutes(5)).subscribe(i -> processWeatherData().subscribe());
  }

  private Flux<Weather> processWeatherData() {

    log.info("Get tracked weather data");
    Flux<Weather> weatherFlux = cityRepository.findAll().flatMap(city ->
        weatherWebService.getCurrentWeatherOpenApi(city.getCityName())
    ).switchIfEmpty(Mono.empty()).log();

    return weatherFlux;
  }


}
