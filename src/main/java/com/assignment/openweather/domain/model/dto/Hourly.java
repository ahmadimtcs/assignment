package com.assignment.openweather.domain.model.dto;

import com.assignment.openweather.Weather;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Hourly {
    int dt;
    double temp;
    double feels_like;
    int pressure;
    int humidity;
    double dew_point;
    double uvi;
    int clouds;
    int visibility;
    double wind_speed;
    int wind_deg;
    double wind_gust;
    ArrayList<Weather> weather;
    double pop;
    Rain rain;

    @JsonProperty("dt")
    public int getDt() {
        return this.dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    @JsonProperty("temp")
    public double getTemp() {
        return this.temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    @JsonProperty("feels_like")
    public double getFeels_like() {
        return this.feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    @JsonProperty("pressure")
    public int getPressure() {
        return this.pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    @JsonProperty("humidity")
    public int getHumidity() {
        return this.humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @JsonProperty("dew_point")
    public double getDew_point() {
        return this.dew_point;
    }

    public void setDew_point(double dew_point) {
        this.dew_point = dew_point;
    }

    @JsonProperty("uvi")
    public double getUvi() {
        return this.uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    @JsonProperty("clouds")
    public int getClouds() {
        return this.clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    @JsonProperty("visibility")
    public int getVisibility() {
        return this.visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    @JsonProperty("wind_speed")
    public double getWind_speed() {
        return this.wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    @JsonProperty("wind_deg")
    public int getWind_deg() {
        return this.wind_deg;
    }

    public void setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
    }

    @JsonProperty("wind_gust")
    public double getWind_gust() {
        return this.wind_gust;
    }

    public void setWind_gust(double wind_gust) {
        this.wind_gust = wind_gust;
    }

    @JsonProperty("weather")
    public ArrayList<com.assignment.openweather.Weather> getWeather() {
        return this.weather;
    }

    public void setWeather(ArrayList<com.assignment.openweather.Weather> weather) {
        this.weather = weather;
    }

    @JsonProperty("pop")
    public double getPop() {
        return this.pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    @JsonProperty("rain")
    public Rain getRain() {
        return this.rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }
}
