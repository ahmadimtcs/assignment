package com.weather.assignment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class WeatherDetailsDto {

  private Long id;

  private Double tempMax;

  private Double tempMin;

  private String name;

  private Double temp;

  private Date date;
}
