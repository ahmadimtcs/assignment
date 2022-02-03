package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyDTO {

  private int dt;
  private int sunrise;
  private int sunset;
  private int moonrise;
  private int moonSet;
  private double moonPhase;
  private TempDTO temp;
  private FeelsLikeDTO feelsLike;
  private int pressure;
  private int humidity;
  private double dewPoint;
  private double windSpeed;
  private int windDeg;
  private double windGust;
  private List<WeatherDTO> weather;
  private int clouds;
  private double pop;
  private double uvi;
  private double rain;

  @JsonProperty("dt")
  public int getDt() {
    return this.dt;
  }

  public void setDt(int dt) {
    this.dt = dt;
  }

  @JsonProperty("sunrise")
  public int getSunrise() {
    return this.sunrise;
  }

  public void setSunrise(int sunrise) {
    this.sunrise = sunrise;
  }

  @JsonProperty("sunset")
  public int getSunset() {
    return this.sunset;
  }

  public void setSunset(int sunset) {
    this.sunset = sunset;
  }

  @JsonProperty("moonrise")
  public int getMoonrise() {
    return this.moonrise;
  }

  public void setMoonrise(int moonrise) {
    this.moonrise = moonrise;
  }

  @JsonProperty("moonset")
  public int getMoonSet() {
    return this.moonSet;
  }

  public void setMoonSet(int moonSet) {
    this.moonSet = moonSet;
  }

  @JsonProperty("moon_phase")
  public double getMoonPhase() {
    return this.moonPhase;
  }

  public void setMoonPhase(double moonPhase) {
    this.moonPhase = moonPhase;
  }

  @JsonProperty("temp")
  public TempDTO getTemp() {
    return this.temp;
  }

  public void setTemp(TempDTO temp) {
    this.temp = temp;
  }

  @JsonProperty("feels_like")
  public FeelsLikeDTO getFeelsLike() {
    return this.feelsLike;
  }

  public void setFeelsLike(FeelsLikeDTO feelsLike) {
    this.feelsLike = feelsLike;
  }

  @JsonProperty("pressure")
  public int getPressure() {
    return this.pressure;
  }

  public void setPressure(int pressure) {
    this.pressure = pressure;
  }

  @JsonProperty("humidity")
  public int getHumidity() {
    return this.humidity;
  }

  public void setHumidity(int humidity) {
    this.humidity = humidity;
  }

  @JsonProperty("dew_point")
  public double getDewPoint() {
    return this.dewPoint;
  }

  public void setDewPoint(double dewPoint) {
    this.dewPoint = dewPoint;
  }

  @JsonProperty("wind_speed")
  public double getWindSpeed() {
    return this.windSpeed;
  }

  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }

  @JsonProperty("wind_deg")
  public int getWindDeg() {
    return this.windDeg;
  }

  public void setWindDeg(int windDeg) {
    this.windDeg = windDeg;
  }

  @JsonProperty("wind_gust")
  public double getWindGust() {
    return this.windGust;
  }

  public void setWindGust(double windGust) {
    this.windGust = windGust;
  }

  @JsonProperty("weather")
  public List<WeatherDTO> getWeather() {
    return this.weather;
  }

  public void setWeather(ArrayList<WeatherDTO> weather) {
    this.weather = weather;
  }

  @JsonProperty("clouds")
  public int getClouds() {
    return this.clouds;
  }

  public void setClouds(int clouds) {
    this.clouds = clouds;
  }

  @JsonProperty("pop")
  public double getPop() {
    return this.pop;
  }

  public void setPop(double pop) {
    this.pop = pop;
  }

  @JsonProperty("uvi")
  public double getUvi() {
    return this.uvi;
  }

  public void setUvi(double uvi) {
    this.uvi = uvi;
  }

  @JsonProperty("rain")
  public double getRain() {
    return this.rain;
  }

  public void setRain(double rain) {
    this.rain = rain;
  }
}
