package com.assignment.openweather.rest.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {

  @NotNull(message = "Locations not be null or empty")
  private List<String> locations;

}
