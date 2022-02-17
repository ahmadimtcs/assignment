package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeelsLikeDTO {

  @JsonProperty("day")
  private double day;
  @JsonProperty("night")
  private double night;
  @JsonProperty("eve")
  private double eve;
  @JsonProperty("morn")
  private double morn;

  public double getDay() {
    return this.day;
  }

  public void setDay(double day) {
    this.day = day;
  }

  public double getNight() {
    return this.night;
  }

  public void setNight(double night) {
    this.night = night;
  }

  public double getEve() {
    return this.eve;
  }

  public void setEve(double eve) {
    this.eve = eve;
  }

  public double getMorn() {
    return this.morn;
  }

  public void setMorn(double morn) {
    this.morn = morn;
  }
}
