package com.mahesh.weather.handler;

import com.mahesh.weather.exceptionhandler.BadDataException;
import com.mahesh.weather.exceptionhandler.ResourceNotFoundException;
import com.mahesh.weather.model.City;
import com.mahesh.weather.service.CityService;
import com.mahesh.weather.service.UserCityDetailsService;
import com.mahesh.weather.util.CommonUtil;
import java.util.HashSet;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserCityHandler {

  private CityService cityService;

  private Validator validator;

  private UserCityDetailsService userCityDetailsService;

  UserCityHandler(CityService cityService, Validator validator,
      UserCityDetailsService userCityDetailsService) {
    this.cityService = cityService;
    this.validator = validator;
    this.userCityDetailsService = userCityDetailsService;
  }

  public Mono<ServerResponse> findCity(ServerRequest request) {

    return request.bodyToMono(City.class)
        .flatMap(this::validateCity)
        .flatMap(cityService::findCityByNameInOpenWeather)
        .flatMap(value -> ServerResponse.ok().bodyValue(value))
        .switchIfEmpty(
            Mono.defer(() -> Mono.error(new ResourceNotFoundException("City details are invalid"))))
        .log();
  }

  public Mono<City> validateCity(City city) {
    var constraintViolations = validator.validate(city);

    if (CollectionUtils.isNotEmpty(constraintViolations)) {
      var errorMessage = constraintViolations
          .stream()
          .map(ConstraintViolation::getMessage)
          .collect(Collectors.joining(", "));
      log.info("Constraint Violations are {}", errorMessage);
      throw new BadDataException(errorMessage);
    }
    return Mono.just(city);
  }

  public Mono<ServerResponse> addUserCities(ServerRequest request) {

    return request.bodyToFlux(City.class)
        .flatMap(city -> {
          log.info("City Name :=> {} Country:=>  {}", city.getName(), city.getCountry());
          return cityService.findByNameAndCountry(StringUtils.capitalize(city.getName()),
                  StringUtils.upperCase(city.getCountry()))
              .switchIfEmpty(Mono.defer(() -> cityService.checkCityInOpenWeather(city)));
        }).collect(Collectors.toSet())
        .flatMap(cities -> {
          log.info("Cities size to be added :=> {}", cities.size());
          return CommonUtil.getLoggedInUserId()
              .flatMap(userCityDetailsService::findOrCreateUserCityDetails)
              .flatMap(userCityDetails -> {
                var citySet = CollectionUtils.isNotEmpty(userCityDetails.getCities())
                    ? userCityDetails.getCities() : new HashSet<City>();
                log.info("Cities size Before update :=> {}", userCityDetails.getCities().size());
                citySet.addAll(cities);
                userCityDetails.setCities(citySet);
                log.info("Cities size After update :=> {}", userCityDetails.getCities().size());
                return userCityDetailsService.save(userCityDetails);
              });
        })
        .flatMap(userCityDetails -> ServerResponse.ok().bodyValue(userCityDetails))
        .switchIfEmpty(
            Mono.defer(() -> Mono.error(new ResourceNotFoundException("City details are invalid"))))
        .log();
  }

  //TODO: Only one city is getting saved in database.
  // userCityDetailsService::findOrCreateUserCityDetails is always returning same value for all elements of Flux
  public Mono<ServerResponse> addUserCitiesFlux(ServerRequest request) {

    return request.bodyToFlux(City.class)
        .flatMap(city -> {
          log.info("City Name :=> {} Country:=>  {}", city.getName(), city.getCountry());
          return cityService.findByNameAndCountry(StringUtils.capitalize(city.getName()),
                  StringUtils.upperCase(city.getCountry()))
              .switchIfEmpty(Mono.defer(() -> cityService.checkCityInOpenWeather(city)));
        })
        .map(city -> {
          CommonUtil.getLoggedInUserId()
              .flatMap(userCityDetailsService::findOrCreateUserCityDetails)
              .map(userCityDetails -> {
                log.info("Cities size Before update :=> {}", userCityDetails.getCities().size());
                userCityDetails.addCity(city);
                log.info("Cities size After update :=> {}", userCityDetails.getCities().size());
                userCityDetailsService.update(userCityDetails).subscribe();
                return userCityDetails;
              })
              .switchIfEmpty(Mono.defer(() -> {
                log.info("User City Details are not Found");
                return Mono.empty();
              })).subscribe();
          return city;
        })
        .collectList()
        .flatMap(citiesSaved -> ServerResponse.ok().bodyValue(citiesSaved))
        .log();
  }

  public Mono<ServerResponse> findAllUserCities(ServerRequest request) {
    return CommonUtil.getLoggedInUserId()
        .flatMap(userCityDetailsService::findUserCityDetails)
        .flatMap(value -> ServerResponse.ok().bodyValue(value))
        .switchIfEmpty(Mono.defer(
            () -> Mono.error(new ResourceNotFoundException("City details are not found for user"))))
        .log();
  }

  public Mono<ServerResponse> addUserCity(ServerRequest request) {
    return request.bodyToMono(City.class)
        .flatMap(city -> cityService.findByNameAndCountry(city.getName(), city.getCountry())
            .switchIfEmpty(Mono.defer(() -> cityService.checkCityInOpenWeather(city)))
            .log())
        .flatMap(city -> CommonUtil.getLoggedInUserId()
            .flatMap(userCityDetailsService::findOrCreateUserCityDetails)
            .flatMap(userCityDetails -> {
              var cities = CollectionUtils.isNotEmpty(userCityDetails.getCities())
                  ? userCityDetails.getCities() : new HashSet<City>();
              cities.add(city);
              userCityDetails.setCities(cities);
              return userCityDetailsService.save(userCityDetails);
            }))
        .flatMap(userCityDetails -> ServerResponse.ok().bodyValue(userCityDetails))
        .switchIfEmpty(
            Mono.defer(() -> Mono.error(new ResourceNotFoundException("City details are invalid"))))
        .log();
  }

  public Mono<ServerResponse> updateUserCities(ServerRequest request) {

    return request.bodyToFlux(City.class)
        .flatMap(city -> {
          log.info("City Name :=> {} Country:=>  {}", city.getName(), city.getCountry());
          return cityService.findByNameAndCountry(StringUtils.capitalize(city.getName()),
                  StringUtils.upperCase(city.getCountry()))
              .switchIfEmpty(Mono.defer(() -> cityService.checkCityInOpenWeather(city)));
        }).collect(Collectors.toSet())
        .flatMap(cities -> {
          log.info("Cities size to be added :=> {}", cities.size());
          return CommonUtil.getLoggedInUserId()
              .flatMap(userCityDetailsService::findOrCreateUserCityDetails)
              .flatMap(userCityDetails -> {
                log.info("Cities size Before update :=> {}", userCityDetails.getCities().size());
                var citySet = new HashSet<>(cities);
                userCityDetails.setCities(citySet);
                log.info("Cities size After update :=> {}", userCityDetails.getCities().size());
                return userCityDetailsService.save(userCityDetails);
              });
        })
        .flatMap(userCityDetails -> ServerResponse.ok().bodyValue(userCityDetails))
        .switchIfEmpty(
            Mono.defer(() -> Mono.error(new ResourceNotFoundException("City details are invalid"))))
        .log();
  }

  public Mono<ServerResponse> deleteUserCities() {

    return CommonUtil.getLoggedInUserId()
        .flatMap(userCityDetailsService::findUserCityDetails)
        .flatMap(userCityDetails -> {
          log.info("UserCityDetails Id :=> {}", userCityDetails.getId());
          userCityDetailsService.removeById(userCityDetails.getId()).log();
          return ServerResponse.ok().build();
        })
        .switchIfEmpty(Mono.defer(
            () -> Mono.error(new ResourceNotFoundException("City details are not found for user"))))
        .log();
  }

  public Mono<ServerResponse> removeAllCitiesForUser(ServerRequest request) {
    return CommonUtil.getLoggedInUserId()
        .flatMap(userCityDetailsService::findOrCreateUserCityDetails)
        .flatMap(userCityDetails -> {
          log.info("Cities size Before update :=> {}", userCityDetails.getCities().size());
          userCityDetails.setCities(new HashSet<>());
          log.info("Cities size After update :=> {}", userCityDetails.getCities().size());
          return userCityDetailsService.save(userCityDetails);
        })
        .flatMap(userCityDetails -> ServerResponse.ok().bodyValue(userCityDetails))
        .switchIfEmpty(Mono.defer(() ->
            Mono.error(new ResourceNotFoundException("City details are not found for user"))))
        .log();
  }
}
