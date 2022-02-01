package com.assignment.openweather.domain.model.dto;

import com.assignment.openweather.Weather;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Daily {
    int dt;
    int sunrise;
    int sunset;
    int moonrise;
    int moonset;
    double moon_phase;
    Temp temp;
    FeelsLike feels_like;
    int pressure;
    int humidity;
    double dew_point;
    double wind_speed;
    int wind_deg;
    double wind_gust;
    ArrayList<Weather> weather;
    int clouds;
    double pop;
    double uvi;
    double rain;

    @JsonProperty("dt")
    public int getDt() {
        return this.dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    @JsonProperty("sunrise")
    public int getSunrise() {
        return this.sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    @JsonProperty("sunset")
    public int getSunset() {
        return this.sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    @JsonProperty("moonrise")
    public int getMoonrise() {
        return this.moonrise;
    }

    public void setMoonrise(int moonrise) {
        this.moonrise = moonrise;
    }

    @JsonProperty("moonset")
    public int getMoonset() {
        return this.moonset;
    }

    public void setMoonset(int moonset) {
        this.moonset = moonset;
    }

    @JsonProperty("moon_phase")
    public double getMoon_phase() {
        return this.moon_phase;
    }

    public void setMoon_phase(double moon_phase) {
        this.moon_phase = moon_phase;
    }

    @JsonProperty("temp")
    public Temp getTemp() {
        return this.temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    @JsonProperty("feels_like")
    public FeelsLike getFeels_like() {
        return this.feels_like;
    }

    public void setFeels_like(FeelsLike feels_like) {
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

    @JsonProperty("clouds")
    public int getClouds() {
        return this.clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    @JsonProperty("pop")
    public double getPop() {
        return this.pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    @JsonProperty("uvi")
    public double getUvi() {
        return this.uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    @JsonProperty("rain")
    public double getRain() {
        return this.rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }
}
