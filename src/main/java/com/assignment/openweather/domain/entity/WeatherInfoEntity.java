package com.assignment.openweather.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfoEntity {
  private int id;
  private String main;
  private String description;
  private String icon;
}
