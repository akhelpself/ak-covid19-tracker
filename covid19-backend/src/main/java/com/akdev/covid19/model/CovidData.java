package com.akdev.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CovidData {

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("confirmed")
    private int confirmed;

    @JsonProperty("deaths")
    private int deaths;

    @JsonProperty("recovered")
    private int recovered;

    @JsonProperty(value = "data")
    private Location location;

}