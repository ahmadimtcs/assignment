package com.clairvoyant.weather.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {

  @Id
  private Long id;
  private String name;
  private Double temp;
  private Double feels_like;


  public City(String name, double temp, double feels_like) {

    this.name = name;
    this.temp = temp;
    this.feels_like = feels_like;
  }


}
