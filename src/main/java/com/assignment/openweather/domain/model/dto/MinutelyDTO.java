package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MinutelyDTO {

  int dt;
  int precipitation;

  @JsonProperty("dt")
  public int getDt() {
    return this.dt;
  }

  public void setDt(int dt) {
    this.dt = dt;
  }

  @JsonProperty("precipitation")
  public int getPrecipitation() {
    return this.precipitation;
  }

  public void setPrecipitation(int precipitation) {
    this.precipitation = precipitation;
  }
}
