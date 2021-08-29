package com.weather.assignment.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDetails {

  @Id private Long id;

  @JsonProperty("temp_max")
  private Double tempMax;

  @JsonProperty("temp_min")
  private Double tempMin;

  @JsonProperty("name")
  private String name;

  @JsonProperty("temp")
  private Double temp;

  private Date date;
}
