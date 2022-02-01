package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class List {
    int id;
    String name;
    Coord coord;
    Main main;
    int dt;
    Wind wind;
    Sys sys;
    Object rain;
    Object snow;
    Clouds clouds;
    ArrayList<Weather> weather;

    @JsonProperty("id")
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("coord")
    public Coord getCoord() {
        return this.coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @JsonProperty("main")
    public Main getMain() {
        return this.main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @JsonProperty("dt")
    public int getDt() {
        return this.dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    @JsonProperty("wind")
    public Wind getWind() {
        return this.wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @JsonProperty("sys")
    public Sys getSys() {
        return this.sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    @JsonProperty("rain")
    public Object getRain() {
        return this.rain;
    }

    public void setRain(Object rain) {
        this.rain = rain;
    }

    @JsonProperty("snow")
    public Object getSnow() {
        return this.snow;
    }

    public void setSnow(Object snow) {
        this.snow = snow;
    }

    @JsonProperty("clouds")
    public Clouds getClouds() {
        return this.clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    @JsonProperty("weather")
    public ArrayList<Weather> getWeather() {
        return this.weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }
}
