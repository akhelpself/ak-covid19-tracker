package com.akdev.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LatestData {

    @JsonProperty("confirmed")
    private int confirmed;

    @JsonProperty("deaths")
    private int deaths;

    @JsonProperty("recovered")
    private int recovered;

}