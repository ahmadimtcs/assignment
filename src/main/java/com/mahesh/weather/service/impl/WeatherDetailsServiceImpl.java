package com.mahesh.weather.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mahesh.weather.client.OpenWeatherRestClient;
import com.mahesh.weather.model.City;
import com.mahesh.weather.model.WeatherDetails;
import com.mahesh.weather.repository.WeatherDetailsReactiveRepository;
import com.mahesh.weather.service.WeatherDetailsService;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WeatherDetailsServiceImpl implements WeatherDetailsService {

  Cache caffeineCache;

  @Autowired
  WeatherDetailsReactiveRepository weatherDetailsReactiveRepository;

  @Autowired
  OpenWeatherRestClient openWeatherRestClient;

  //  @Value(value = "${key.refresh-interval}")
  private Long REFRESH_INTERVAL;


  public WeatherDetailsServiceImpl(
      @Value(value = "${key.refresh-interval}") String refreshInterval) {
    REFRESH_INTERVAL = Objects.nonNull(refreshInterval) ? Long.parseLong(refreshInterval) : 2l;
    log.info("Refresh interval Value :=> {}", refreshInterval);
    this.caffeineCache = Caffeine.newBuilder()
        .expireAfterWrite(REFRESH_INTERVAL, TimeUnit.MINUTES)
        .initialCapacity(100)
        .maximumSize(500)
        .recordStats()
        .build();
  }

  @Override
  public Mono<WeatherDetails> save(WeatherDetails weatherDetails) {
    return weatherDetailsReactiveRepository.save(weatherDetails).log();
  }

  @Override
  public Mono<WeatherDetails> findByCityNameAndCountry(String cityName, String country) {
    return weatherDetailsReactiveRepository.findByCityNameAndCityCountry(cityName, country)
        .switchIfEmpty(Mono.empty())
        .log();
  }

  @Override
  public Mono<WeatherDetails> findByLatAndLon(String lat, String lon) {
    return weatherDetailsReactiveRepository.findByCoordinateLatAndCoordinateLon(lat, lon);
  }

  @Override
  public Mono<WeatherDetails> getOpenWeatherForCity(City city) {
    var weatherDetails = openWeatherRestClient.getWeatherOfCity(city)
        .map(weatherDetails1 -> {
          log.info("Fetched Weather Details from OpenWeaher");
          weatherDetails1.setCity(city);
          return weatherDetails1;
        })
        .flatMap(this::save)
        .log();
    return weatherDetails;
  }

  @Override
  public Mono<WeatherDetails> update(WeatherDetails wd) {
    return weatherDetailsReactiveRepository.findById(wd.getWeatherId())
        .flatMap(weatherDetails -> {
          log.info("Updating values of Mongo");
          weatherDetails.setWeather(wd.getWeather());
          weatherDetails.setCity(wd.getCity());
          weatherDetails.setDt(wd.getDt());
          weatherDetails.setSys(wd.getSys());
          weatherDetails.setMain(wd.getMain());
          weatherDetails.setVisibility(wd.getVisibility());
          weatherDetails.setWind(wd.getWind());
          //weatherDetailsReactiveRepository.deleteById(weatherDetails.getWeatherId()).subscribe();
          return this.save(weatherDetails);
        })
        .switchIfEmpty(Mono.empty());
  }

  @Override
  @Cacheable(key = "#city.name#city.country")
  public Mono<WeatherDetails> findByCityNameAndCountryWithCache(City city) {
    return CacheMono.lookup(caffeineCache.asMap(), city.getName() + city.getCountry())
        .onCacheMissResume(() -> {
          log.info("Fetching weather for City as it is not present in cache");
          return this.getOpenWeatherForCity(city)
              .log();
        });
  }
}
