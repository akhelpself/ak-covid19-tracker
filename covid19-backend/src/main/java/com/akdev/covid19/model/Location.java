package com.akdev.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;

@Data
public class Location {

    @JsonProperty("coordinates")
    private Coordinates coordinates;

    @JsonProperty("country")
    private String country;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("history")
    private HashMap<String, String> history;

    @JsonProperty("province")
    private String province;

}