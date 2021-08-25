package com.clairvoyant.weather.dto;

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
public class WeatherDto {

  private Long id;
  private String name;
  private Double temp;
  private Double feelsLike;
}
