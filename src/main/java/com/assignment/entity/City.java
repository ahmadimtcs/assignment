package com.assignment.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City {

  @Id private Long id;
  private String name;
  private Double temp;
  private Double feels_like;
  private Double temp_min;
  private Double temp_max;
  private Double pressure;
  private Long humidity;
  private Long sea_level;
  private Long grnd_level;
  private String country;
}
