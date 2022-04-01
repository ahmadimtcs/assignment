package com.mahesh.weather.model;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@Builder
@Document
public class City {

  @Id
  private String id;

  @NotBlank(message = "city.name should not be blank")
  private String name;

  private String lat;

  private String lon;

  @NotBlank(message = "city.country should not be blank")
  private String country;

  @CreatedBy
  private String createdBy;

  @LastModifiedBy
  private String modifiedBy;

  @CreatedDate
  private Date dateCreated;

  @LastModifiedDate
  private Date dateModified;

  public City(String id, String name, String lat, String lon, String country) {
    this.id = id;
    this.name = name;
    this.lat = lat;
    this.lon = lon;
    this.country = country;
  }
}
