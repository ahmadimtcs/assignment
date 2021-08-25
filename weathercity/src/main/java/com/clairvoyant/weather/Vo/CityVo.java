package com.clairvoyant.weather.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityVo {

  private Long id;
  private String name;
  private Double temp;
  private Double feels_like;
}
