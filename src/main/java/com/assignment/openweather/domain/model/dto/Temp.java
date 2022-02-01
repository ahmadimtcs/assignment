package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Temp {
    double day;
    double min;
    double max;
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

    @JsonProperty("min")
    public double getMin() {
        return this.min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    @JsonProperty("max")
    public double getMax() {
        return this.max;
    }

    public void setMax(double max) {
        this.max = max;
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
