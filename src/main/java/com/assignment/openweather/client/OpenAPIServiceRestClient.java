package com.assignment.openweather.client;

import static java.util.Collections.singletonList;

import com.assignment.openweather.config.OpenAPIEnvironmentPropertyConfig;
import com.assignment.openweather.domain.model.dto.SearchDataResponseDTO;
import com.assignment.openweather.domain.model.dto.WeatherDataResponseDTO;
import com.assignment.openweather.exception.ClientException;
import com.assignment.openweather.exception.ServiceException;
import com.assignment.openweather.utils.RetryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class OpenAPIServiceRestClient {

  @Autowired
  private WebClient webClient;

  @Autowired
  private OpenAPIEnvironmentPropertyConfig openAPIEnvironmentPropertyConfig;


  @Cacheable(value = "searchLocationData", key = "T(com.assignment.openweather.utils.Md5Util).generateKey(#locationName)", unless = "#result == null")
  public Mono<SearchDataResponseDTO> searchData(String locationName) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.put("q", singletonList(locationName));
    queryParams.put("appid", singletonList(openAPIEnvironmentPropertyConfig.getAppId()));
    queryParams.put("units", singletonList(openAPIEnvironmentPropertyConfig.getUnits()));

    String url = UriComponentsBuilder.fromUriString(
            openAPIEnvironmentPropertyConfig.getRestClient().getSearchUrl()).queryParams(queryParams)
        .build().toUriString();
    return webClient.get()
        .uri(url)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, (clientResponse -> {
          log.info("Status code : {}", clientResponse.statusCode().value());
          if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
            return Mono.error(new ClientException(
                "There is no Location information available for the passed in location data : "
                    + locationName, clientResponse.statusCode().value()));
          }
          return clientResponse.bodyToMono(String.class)
              .flatMap(response -> Mono.error(
                  new ClientException(response, clientResponse.statusCode().value())));
        }))
        .onStatus(HttpStatus::is5xxServerError, (clientResponse -> {
          log.info("Status code : {}", clientResponse.statusCode().value());
          return clientResponse.bodyToMono(String.class)
              .flatMap(response -> Mono.error(new ServiceException(response)));
        }))
        .bodyToMono(SearchDataResponseDTO.class)
        .retryWhen(RetryUtil.retrySpec())
        .log();
  }


  @Cacheable(value = "weatherLocationData", key = "T(com.assignment.openweather.utils.Md5Util).generateKey(#latitude,#longitude)", unless = "#result == null")
  public Mono<WeatherDataResponseDTO> getWeatherData(String latitude, String longitude) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.put("lat", singletonList(latitude));
    queryParams.put("lon", singletonList(longitude));
    queryParams.put("appid", singletonList(openAPIEnvironmentPropertyConfig.getAppId()));
    queryParams.put("units", singletonList(openAPIEnvironmentPropertyConfig.getUnits()));

    String url = UriComponentsBuilder.fromUriString(
            openAPIEnvironmentPropertyConfig.getRestClient().getDataUrl()).queryParams(queryParams)
        .build().toUriString();
    return webClient.get()
        .uri(url)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, (clientResponse -> {
          log.info("Status code : {}", clientResponse.statusCode().value());
          if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
            return Mono.error(new ClientException(
                String.format(
                    "There is no Weather information available for the passed in location data : lat - %s , lon - %s",
                    latitude, longitude), clientResponse.statusCode().value()));
          }
          return clientResponse.bodyToMono(String.class)
              .flatMap(response -> Mono.error(
                  new ClientException(response, clientResponse.statusCode().value())));
        }))
        .onStatus(HttpStatus::is5xxServerError, (clientResponse -> {
          log.info("Status code : {}", clientResponse.statusCode().value());
          return clientResponse.bodyToMono(String.class)
              .flatMap(response -> Mono.error(new ServiceException(response)));
        }))
        .bodyToMono(WeatherDataResponseDTO.class)
        .retryWhen(RetryUtil.retrySpec())
        .log();
  }


}
