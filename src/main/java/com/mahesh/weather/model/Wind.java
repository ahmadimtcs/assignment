package com.mahesh.weather.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Wind {

  private Double speed;

  private Double deg;
}
