package com.mahesh.weather.handler;

import com.mahesh.weather.exceptionhandler.ResourceNotFoundException;
import com.mahesh.weather.model.City;
import com.mahesh.weather.service.UserCityDetailsService;
import com.mahesh.weather.service.WeatherDetailsService;
import com.mahesh.weather.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class WeatherHandler {

  private UserCityDetailsService userCityDetailsService;

  private WeatherDetailsService weatherDetailsService;

  private Long REFRESH_INTERVAL;

  WeatherHandler(UserCityDetailsService userCityDetailsService,
      WeatherDetailsService weatherDetailsService,
      @Value(value = "${key.refresh-interval}") String refreshInterval) {
    this.userCityDetailsService = userCityDetailsService;
    this.weatherDetailsService = weatherDetailsService;
    this.REFRESH_INTERVAL = Long.parseLong(refreshInterval);
  }


  public Mono<ServerResponse> getWeatherOfAllUserCitiesWithCache() {
    return CommonUtil.getLoggedInUserId()
        .flatMap(userId -> userCityDetailsService.findUserCityDetails(userId))
        .flatMapMany(userCityDetails -> {
          var cities = userCityDetails.getCities();
          return Flux.fromIterable(cities);
        })
        .flatMap(city -> weatherDetailsService.findByCityNameAndCountryWithCache(city).log())
        .collectList()
        .flatMap(weatherDetails -> ServerResponse.ok().bodyValue(weatherDetails))
        .switchIfEmpty(Mono.defer(() -> Mono.error(
            new ResourceNotFoundException("Weather details are not found for user cities"))))
        .log();
  }

  /*public Mono<ServerResponse> getWeatherOfAllUserCities() {
    var response = CommonUtil.getLoggedInUserId()
        .flatMap(userId -> userCityDetailsService.findUserCityDetails(userId))
        .flatMapMany(userCityDetails -> {
          var cities = userCityDetails.getCities();
          return Flux.fromIterable(cities);
        })
        .flatMap(city -> {
          var weatherDetailsMongo = //weatherDetailsService.findByLatAndLon(city.getLat(), city.getLon())
              weatherDetailsService.findByCityNameAndCountry(StringUtils.capitalize(city.getName()),
                      StringUtils.upperCase(city.getCountry()))
                  .switchIfEmpty(Mono.defer(() -> weatherDetailsService.getOpenWeatherForCity(city)))
                  .log();
          return weatherDetailsMongo;
        })
//                .map(weather -> {
//                    long currentTimeStamp = Calendar.getInstance().getTimeInMillis();
//                    long lastRefreshedTime = Long.parseLong(weather.getDt())*1000;
//                    if((currentTimeStamp - lastRefreshedTime) > (Integer.parseInt(REFRESH_INTERVAL) * 60000)) {
//                        log.info("Getting latest weather information from OpenWeather");
//                        var updatedWeather = this.getOpenWeatherForCity(weather.getCity())
//                                .switchIfEmpty(Mono.defer(() -> Mono.empty()))
//                                .flatMap(weatherDetails1 -> {
//                                    weatherDetails1.setWeatherId(weather.getWeatherId());
//                                    return weatherDetailsService.save(weatherDetails1);
//                                })
//                                .map(weatherDetails1 -> {
//                                    log.info("Updating Weather values");
//                                    weather.setWeather(weatherDetails1.getWeather());
//                                    weather.setDt(weatherDetails1.getDt());
//                                    weather.setMain(weatherDetails1.getMain());
//                                    weather.setSys(weatherDetails1.getSys());
//                                    weather.setVisibility(weatherDetails1.getVisibility());
//                                    weather.setWind(weatherDetails1.getWind());
//                                    return weatherDetails1;
//                                }).log();
//                        log.info("Updated Weather {}", updatedWeather);
//                    } else {
//                        log.info("No need to get latest weather");
//                    }
//                    log.info("Weather Document Id {}", weather.getWeatherId());
//                    log.info("Weather Details of city {}", weather.getName());
//                    log.info("Weather Details Visibility {}", weather.getVisibility());
//                    return weather;
//                })
        .log();

    return response
        .collectList()
        .flatMap(weatherDetails -> ServerResponse.ok().bodyValue(weatherDetails))
        .switchIfEmpty(Mono.defer(() -> Mono.error(
            new ResourceNotFoundException("Weather details are not found for user cities"))))
        .log();
  }

  public Mono<ServerResponse> getWeatherForCity(ServerRequest request) {
    return CommonUtil.getLoggedInUserId()
        .flatMap(userId -> userCityDetailsService.findUserCityDetails(userId))
        .flatMap(userCityDetails -> {
          var cities = userCityDetails.getCities();
          return request.bodyToMono(City.class)
              .flatMap(city -> {
                var matchedCity = cities.stream().filter(
                        cityPredicate -> cityPredicate.getName().equalsIgnoreCase(city.getName())
                            && cityPredicate.getCountry().equalsIgnoreCase(city.getCountry()))
                    .findFirst();
                log.info("Matched City is present {}", matchedCity.isPresent());
                return matchedCity.isPresent() ? Mono.just(matchedCity.get()) : Mono.empty();
              });
        })
        .flatMap(city -> {
          log.info("Calling findByCityNameAndCountry");
          return weatherDetailsService.findByCityNameAndCountry(
                  StringUtils.capitalize(city.getName()), StringUtils.upperCase(city.getCountry()))
              .log();
        })
//        .switchIfEmpty(Mono.defer(() -> {
//              log.info("Getting Latest Weather Information from switchIfEmpty");
//              return request
//                  .bodyToMono(City.class)
//                  .flatMap(city1 -> weatherDetailsService.getOpenWeatherForCity(city1).log())
//                  .log();
//            })
//        )
        .flatMap(weatherDetails -> {
          long lastRefreshedTime = Long.parseLong(weatherDetails.getDt()) * 1000;
          long currentTimeStamp = Calendar.getInstance().getTimeInMillis();
          if ((currentTimeStamp - lastRefreshedTime) > (Integer.parseInt(REFRESH_INTERVAL)
              * 60000)) {
            log.info("Getting latest weather information from OpenWeather");
            return weatherDetailsService.getOpenWeatherForCity(weatherDetails.getCity())
                .flatMap(wd -> {
                  wd.setWeatherId(weatherDetails.getWeatherId());
                  return weatherDetailsService.update(wd).log();
                })
                .switchIfEmpty(Mono.defer(() -> Mono.empty()))
                .log();
          } else {
            log.info("No need to get latest weather information from OpenWeather");
            return Mono.just(weatherDetails);
          }
        })
        .flatMap(weatherDetails -> ServerResponse.ok().bodyValue(weatherDetails))
        .switchIfEmpty(Mono.defer(() -> Mono.error(
            new ResourceNotFoundException("Weather details are not found for user city"))))
        .log();
  }
*/

  public Mono<ServerResponse> getWeatherForCityWithCache(ServerRequest request) {
    return CommonUtil.getLoggedInUserId()
        .flatMap(userId -> userCityDetailsService.findUserCityDetails(userId))
        .flatMap(userCityDetails -> {
          var cities = userCityDetails.getCities();
          return request.bodyToMono(City.class)
              .flatMap(city -> {
                var matchedCity = cities.stream().filter(
                        cityPredicate -> cityPredicate.getName().equalsIgnoreCase(city.getName())
                            && cityPredicate.getCountry().equalsIgnoreCase(city.getCountry()))
                    .findFirst();
                log.info("Matched City is present {}", matchedCity.isPresent());
                return matchedCity.isPresent() ? Mono.just(matchedCity.get()) : Mono.empty();
              });
        }).flatMap(city -> {
          log.info("Calling findByCityNameAndCountryWithCache");
          return weatherDetailsService.findByCityNameAndCountryWithCache(city)
              .log();
        })
        .flatMap(weatherDetails -> ServerResponse.ok().bodyValue(weatherDetails))
        .switchIfEmpty(Mono.defer(() -> Mono.error(
            new ResourceNotFoundException("Weather details are not found for user city"))))
        .log();
  }

}
