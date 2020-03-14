package com.akdev.covid19tracker.model;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Location {

    @SerializedName("coordinates")
    private Coordinates coordinates;

    @SerializedName("country")
    private String country;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("history")
    private HashMap<String, String> history;

    @SerializedName("province")
    private String province;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public HashMap<String, String> getHistory() {
        return history;
    }

    public void setHistory(HashMap<String, String> history) {
        this.history = history;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}