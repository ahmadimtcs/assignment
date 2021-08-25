package com.clairvoyant.weather.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 25-08-2021 10:55
 */
@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "weather")
public class WeatherProperties {
  private String key;
  private String lon;
  private String lat;
  private Double minutes;
}
