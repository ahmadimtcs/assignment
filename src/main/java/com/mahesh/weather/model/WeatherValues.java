package com.mahesh.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherValues {

  private Double temp;

  @JsonProperty(value = "feels_like")
  private Double feelsLike;

  @JsonProperty(value = "temp_min")
  private Double tempMin;

  @JsonProperty(value = "temp_max")
  private Double tempMax;

  private Double pressure;

  private Double humidity;
}
