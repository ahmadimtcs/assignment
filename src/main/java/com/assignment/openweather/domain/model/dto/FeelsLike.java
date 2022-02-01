package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeelsLike {
    double day;
    double night;
    double eve;
    double morn;

    @JsonProperty("day")
    public double getDay() {
        return this.day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    @JsonProperty("night")
    public double getNight() {
        return this.night;
    }

    public void setNight(double night) {
        this.night = night;
    }

    @JsonProperty("eve")
    public double getEve() {
        return this.eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    @JsonProperty("morn")
    public double getMorn() {
        return this.morn;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }
}
