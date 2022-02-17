package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TempDTO {

  @JsonProperty("day")
  private double day;
  @JsonProperty("min")
  private double min;
  @JsonProperty("max")
  private double max;
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

  public double getMin() {
    return this.min;
  }

  public void setMin(double min) {
    this.min = min;
  }

  public double getMax() {
    return this.max;
  }

  public void setMax(double max) {
    this.max = max;
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
