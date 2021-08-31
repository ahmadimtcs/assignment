package com.clairvoyant.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 17-08-2021 16:20
 */

@Getter
@Setter
@NoArgsConstructor
public class TemperatureDto {

  @JsonProperty(value = "temp")
  private Double temp;
  @JsonProperty(value = "feels_like")
  private Double feelsLike;

}
