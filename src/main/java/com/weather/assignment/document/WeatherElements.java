package com.weather.assignment.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherElements {

    @Id
    private Long id;
    private Double temp_max;
    private Double temp_min;
    private String name;
    private Double temp;
    private Date date;
}