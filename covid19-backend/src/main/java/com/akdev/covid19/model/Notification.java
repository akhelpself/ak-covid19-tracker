package com.akdev.covid19.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Notification {

    private int level;
    private String message;
    private double distance;
    private String address;
    private String neatestLocation;
    private Map<String, Double> relateLocation = new LinkedHashMap<>();

}
