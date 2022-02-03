package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchDataDTO {

  private int id;
  private String name;
  private CoordinatesDTO coordinates;

  @JsonProperty("id")
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @JsonProperty("name")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("coord")
  public CoordinatesDTO getCoordinates() {
    return this.coordinates;
  }

  public void setCoordinates(CoordinatesDTO coordinates) {
    this.coordinates = coordinates;
  }

}
