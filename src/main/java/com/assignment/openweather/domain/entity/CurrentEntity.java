package com.assignment.openweather.domain.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentEntity {
  private int dt;
  private int sunrise;
  private int sunset;
  private double temp;
  private double feelsLike;
  private int pressure;
  private int humidity;
  private double dewPoint;
  private double uvi;
  private int clouds;
  private int visibility;
  private double windSpeed;
  private int windDeg;
  private double windGust;
  private List<WeatherInfoEntity> weather;
}
