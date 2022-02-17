package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchDataResponseDTO {

  @JsonProperty("message")
  private String message;
  @JsonProperty("cod")
  private String cod;
  @JsonProperty("count")
  private int count;
  @JsonProperty("list")
  private ArrayList<SearchDataDTO> list;


  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public String getCod() {
    return this.cod;
  }

  public void setCod(String cod) {
    this.cod = cod;
  }


  public int getCount() {
    return this.count;
  }

  public void setCount(int count) {
    this.count = count;
  }


  public ArrayList<SearchDataDTO> getList() {
    return this.list;
  }

  public void setList(ArrayList<SearchDataDTO> list) {
    this.list = list;
  }
}
