package com.assignment.openweather.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Root {
    String message;
    String cod;
    int count;
    ArrayList<List> list;

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("cod")
    public String getCod() {
        return this.cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    @JsonProperty("count")
    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @JsonProperty("list")
    public ArrayList<List> getList() {
        return this.list;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }
}
