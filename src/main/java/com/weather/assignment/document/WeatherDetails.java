package com.weather.assignment.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDetails {

	@Id
	private Long id;
	private Double temp_max;
	private Double temp_min;
	private String name;
	private Double temp;
	private Date date;

	@Override
	public String toString() {
		return "WeatherDetails{" +
				"id=" + id +
				", temp_max=" + temp_max +
				", temp_min=" + temp_min +
				", name='" + name + '\'' +
				", temp=" + temp +
				", date=" + date +
				'}';
	}
}