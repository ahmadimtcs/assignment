package com.mahesh.weather.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sys {

  private String country;

  private String sunrise;

  private String sunset;
}
