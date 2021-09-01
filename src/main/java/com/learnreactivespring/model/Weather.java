package com.learnreactivespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

  @Id
  String name;
  List<WeatherModel> weather;
  MainModel main;


  public static class MainModel {

    public Double temp;
    public Double feels_like;
    public Double temp_min;
    public Double temp_max;
    public Double pressure;
    public Double humidity;
  }


  public static class WeatherModel {

    public int id;
    public String main;
    public String description;
    public String icon;
  }
}
