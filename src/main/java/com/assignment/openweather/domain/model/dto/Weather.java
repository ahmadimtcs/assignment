package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {
    int id;
    String main;
    String description;
    String icon;

    @JsonProperty("id")
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("main")
    public String getMain() {
        return this.main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    @JsonProperty("description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
