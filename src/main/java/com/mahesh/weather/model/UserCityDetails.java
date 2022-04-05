package com.mahesh.weather.model;

import java.util.Date;
import java.util.Set;
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
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCityDetails {

  @Id
  private String id;
  @NotBlank
  private String userId;

  private Set<City> cities;

  @CreatedBy
  private String createdBy;

  @LastModifiedBy
  private String modifiedBy;

  @CreatedDate
  private Date dateCreated;

  @LastModifiedDate
  private Date dateModified;

  public UserCityDetails(String id, String userId, Set<City> cities) {
    this.id = id;
    this.userId = userId;
    this.cities = cities;
  }

  public void addCity(City city) {
    this.cities.add(city);
  }
}
