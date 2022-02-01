package com.assignment.openweather;

import com.assignment.openweather.domain.model.dto.Current;
import com.assignment.openweather.domain.model.dto.Daily;
import com.assignment.openweather.domain.model.dto.Hourly;
import com.assignment.openweather.domain.model.dto.Minutely;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Root {
    @JsonProperty("lat")
    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    double lat;

    @JsonProperty("lon")
    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    double lon;

    @JsonProperty("timezone")
    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    String timezone;

    @JsonProperty("timezone_offset")
    public int getTimezone_offset() {
        return this.timezone_offset;
    }

    public void setTimezone_offset(int timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    int timezone_offset;

    @JsonProperty("current")
    public Current getCurrent() {
        return this.current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    Current current;

    @JsonProperty("minutely")
    public ArrayList<Minutely> getMinutely() {
        return this.minutely;
    }

    public void setMinutely(ArrayList<Minutely> minutely) {
        this.minutely = minutely;
    }

    ArrayList<Minutely> minutely;

    @JsonProperty("hourly")
    public ArrayList<Hourly> getHourly() {
        return this.hourly;
    }

    public void setHourly(ArrayList<Hourly> hourly) {
        this.hourly = hourly;
    }

    ArrayList<Hourly> hourly;

    @JsonProperty("daily")
    public ArrayList<Daily> getDaily() {
        return this.daily;
    }

    public void setDaily(ArrayList<Daily> daily) {
        this.daily = daily;
    }

    ArrayList<Daily> daily;
}
