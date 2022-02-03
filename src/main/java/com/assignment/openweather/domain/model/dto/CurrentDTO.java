package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentDTO {
  @JsonProperty("dt")
  private int dt;
  @JsonProperty("sunrise")
  private int sunrise;
  @JsonProperty("sunset")
  private int sunset;
  @JsonProperty("temp")
  private double temp;
  @JsonProperty("feels_like")
  private double feelsLike;
  @JsonProperty("pressure")
  private int pressure;
  @JsonProperty("humidity")
  private int humidity;
  @JsonProperty("dew_point")
  private double dewPoint;
  @JsonProperty("uvi")
  private double uvi;
  @JsonProperty("clouds")
  private int clouds;
  @JsonProperty("visibility")
  private int visibility;
  @JsonProperty("wind_speed")
  private double windSpeed;
  @JsonProperty("wind_deg")
  private int windDeg;
  @JsonProperty("wind_gust")
  private double windGust;
  @JsonProperty("weather")
  private List<WeatherDTO> weather;


  public int getDt() {
    return this.dt;
  }

  public void setDt(int dt) {
    this.dt = dt;
  }

  public int getSunrise() {
    return this.sunrise;
  }

  public void setSunrise(int sunrise) {
    this.sunrise = sunrise;
  }

  public int getSunset() {
    return this.sunset;
  }

  public void setSunset(int sunset) {
    this.sunset = sunset;
  }

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

  public double getDewPoint() {
    return this.dewPoint;
  }

  public void setDewPoint(double dewPoint) {
    this.dewPoint = dewPoint;
  }

  public double getUvi() {
    return this.uvi;
  }

  public void setUvi(double uvi) {
    this.uvi = uvi;
  }

  public int getClouds() {
    return this.clouds;
  }

  public void setClouds(int clouds) {
    this.clouds = clouds;
  }

  public int getVisibility() {
    return this.visibility;
  }

  public void setVisibility(int visibility) {
    this.visibility = visibility;
  }

  public double getWindSpeed() {
    return this.windSpeed;
  }

  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }

  public int getWindDeg() {
    return this.windDeg;
  }

  public void setWindDeg(int windDeg) {
    this.windDeg = windDeg;
  }

  public double getWindGust() {
    return this.windGust;
  }

  public void setWindGust(double windGust) {
    this.windGust = windGust;
  }

  public List<WeatherDTO> getWeather() {
    return this.weather;
  }
  public void setWeather(ArrayList<WeatherDTO> weather) {
    this.weather = weather;
  }
}
