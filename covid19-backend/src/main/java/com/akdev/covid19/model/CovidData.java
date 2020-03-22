package com.akdev.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

    @Data
public class CovidData {

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("confirmed")
    private int confirmed;

    @JsonProperty("new_confirmed")
    private int newConfirmed;

    @JsonProperty("deaths")
    private int deaths;

    @JsonProperty(value = "new_deaths")
    private int newDeaths;

    @JsonProperty("recovered")
    private int recovered;

    @JsonProperty(value = "data")
    private Location location;

    @JsonProperty(value = "serious")
    private int serious;

    @JsonProperty(value = "critical")
    private int critical;

    @JsonProperty(value = "percent_of_deaths")
    private String percentOfDeaths;

}