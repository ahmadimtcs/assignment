package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainDTO {

  @JsonProperty("temp")
  private double temp;
  @JsonProperty("feels_like")
  private double feelsLike;
  @JsonProperty("temp_min")
  private double tempMin;
  @JsonProperty("temp_max")
  private double tempMax;
  @JsonProperty("pressure")
  private int pressure;
  @JsonProperty("humidity")
  private int humidity;
  @JsonProperty("sea_level")
  private int seaLevel;
  @JsonProperty("grnd_level")
  private int groundLevel;

  public double getTemp() {
    return this.temp;
  }

  public void setTemp(double temp) {
    this.temp = temp;
  }

  public double getFeelsLike() {
    return this.feelsLike;
  }

  public void setFeelsLike(double feelsLike) {
    this.feelsLike = feelsLike;
  }

  public double getTempMin() {
    return this.tempMin;
  }

  public void setTempMin(double tempMin) {
    this.tempMin = tempMin;
  }

  public double getTempMax() {
    return this.tempMax;
  }

  public void setTempMax(double tempMax) {
    this.tempMax = tempMax;
  }

  public int getPressure() {
    return this.pressure;
  }

  public void setPressure(int pressure) {
    this.pressure = pressure;
  }

  public int getHumidity() {
    return this.humidity;
  }

  public void setHumidity(int humidity) {
    this.humidity = humidity;
  }

  public int getSeaLevel() {
    return this.seaLevel;
  }

  public void setSeaLevel(int seaLevel) {
    this.seaLevel = seaLevel;
  }

  public int getGroundLevel() {
    return this.groundLevel;
  }

  public void setGroundLevel(int groundLevel) {
    this.groundLevel = groundLevel;
  }
}
