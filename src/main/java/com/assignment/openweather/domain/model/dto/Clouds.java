package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clouds {
    int all;

    @JsonProperty("all")
    public int getAll() {
        return this.all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
