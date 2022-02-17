package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysDTO {

  @JsonProperty("country")
  String country;

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
