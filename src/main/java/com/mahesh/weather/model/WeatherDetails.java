package com.mahesh.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class WeatherDetails {

  @Id
  private String weatherId;

  @JsonProperty(value = "coord")
  private Coordinate coordinate;

  private List<Weather> weather;

  private WeatherValues main;

  private Long visibility;

  private Wind wind;

  private String dt;

  private Sys sys;

  private String name;

  @JsonProperty(value = "id")
  private String cityId;

  private City city;

  @CreatedBy
  private String createdBy;

  @LastModifiedBy
  private String modifiedBy;

  @CreatedDate
  private Date dateCreated;

  @LastModifiedDate
  private Date dateModified;

}
