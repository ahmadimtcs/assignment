package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatesDTO {

  @JsonProperty("lat")
  private double lat;
  @JsonProperty("lon")
  private double lon;

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
}
