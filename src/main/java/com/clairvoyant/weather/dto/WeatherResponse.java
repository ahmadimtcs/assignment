package com.clairvoyant.weather.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 27-08-2021 15:45
 */
@Getter
@Setter
@NoArgsConstructor
public class WeatherResponse {
  private String cod;
  private List<WeatherData> list;

}
