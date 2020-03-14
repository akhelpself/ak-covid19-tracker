package com.akdev.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Coordinates {

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lng")
    private String lng;

    public Coordinates() { }

    public Coordinates(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
}