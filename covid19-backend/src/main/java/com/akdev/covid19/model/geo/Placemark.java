package com.akdev.covid19.model.geo;

import lombok.Data;

@Data
public class Placemark {

    private String name;
    private String description;
    private String styleUrl;
    private String coordinates;

    public String key() {
        return name + "[" + coordinates +"]";
    }
}
