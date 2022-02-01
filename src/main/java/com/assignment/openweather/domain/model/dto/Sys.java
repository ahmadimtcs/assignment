package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sys {
    String country;

    @JsonProperty("country")
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
