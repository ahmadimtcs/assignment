package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rain {
    double _1h;

    @JsonProperty("1h")
    public double get_1h() {
        return this._1h;
    }

    public void set_1h(double _1h) {
        this._1h = _1h;
    }
}
