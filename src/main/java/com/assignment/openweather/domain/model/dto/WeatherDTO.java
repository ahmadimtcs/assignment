package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {
  @JsonProperty("id")
  private int id;
  @JsonProperty("main")
  private String main;
  @JsonProperty("description")
  private String description;
  @JsonProperty("icon")
  private String icon;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMain() {
    return this.main;
  }

  public void setMain(String main) {
    this.main = main;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIcon() {
    return this.icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
