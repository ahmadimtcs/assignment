package com.assignment.openweather.domain.entity;


import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Validated
public class WeatherEntity {

  @Id
  @Field(name = "id")
  private String locationId;

  @NotBlank(message = "Location Name should not be null")
  @Field(name = "name")
  private String locationName;

  @Field(name = "created_date")
  private LocalDate createdDate;

  @Field(name = "modified_date")
  private LocalDate modifiedDate;

  @Field(name = "latitude")
  private Double latitude;

  @Field(name = "longitude")
  private Double longitude;

  @Field(name = "time_zone_offset")
  private Integer timezoneOffset;

  @Field(name = "time_zone")
  private String timezone;

  @Field(name = "current")
  private CurrentEntity current;

}
