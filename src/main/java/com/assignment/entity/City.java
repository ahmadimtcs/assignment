package com.assignment.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {



    @Id
    private Long id;
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

    public City(String name, double temp, double feels_like, double temp_min, double temp_max, double pressure
   , Long humidity, Long sea_level, Long grnd_level , String country) {

        this.name = name;
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.pressure = pressure;
        this.humidity=humidity;
        this.sea_level=sea_level;
        this.grnd_level=grnd_level;
        this.country=country;
    }


}

