package com.clairvoyant.weather.document;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Gufran Khan
 * @version 1.0
 * @date 17-08-2021 16:20
 */
@Document
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class WeatherDetails {

	@Id
	private Long id;
	private String name;
	private Double temp;
	private Double feelsLike;
}
