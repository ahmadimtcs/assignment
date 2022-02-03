package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataResponseDTO {

  @JsonProperty("lon")
  private double lon;
  @JsonProperty("lat")
  private double lat;
  @JsonProperty("timezone_offset")
  private int timezoneOffset;
  @JsonProperty("timezone")
  private String timezone;
  @JsonProperty("hourly")
  private List<HourlyDTO> hourly;
  @JsonProperty("daily")
  private List<DailyDTO> daily;
  @JsonProperty("minutely")
  private List<MinutelyDTO> minutely;
  @JsonProperty("current")
  private CurrentDTO current;

  public double getLat() {
    return this.lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLon() {
    return this.lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public String getTimezone() {
    return this.timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public int getTimezoneOffset() {
    return this.timezoneOffset;
  }

  public void setTimezoneOffset(int timezoneOffset) {
    this.timezoneOffset = timezoneOffset;
  }

  public CurrentDTO getCurrent() {
    return this.current;
  }

  public void setCurrent(CurrentDTO current) {
    this.current = current;
  }

  public List<MinutelyDTO> getMinutely() {
    return this.minutely;
  }

  public void setMinutely(ArrayList<MinutelyDTO> minutely) {
    this.minutely = minutely;
  }

  public List<HourlyDTO> getHourly() {
    return this.hourly;
  }

  public void setHourly(ArrayList<HourlyDTO> hourly) {
    this.hourly = hourly;
  }

  public List<DailyDTO> getDaily() {
    return this.daily;
  }

  public void setDaily(ArrayList<DailyDTO> daily) {
    this.daily = daily;
  }
}
