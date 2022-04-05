package com.mahesh.weather.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Weather {

  private String id;
  private String main;
  private String description;
  private String icon;
}
