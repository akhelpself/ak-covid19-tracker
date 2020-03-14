package com.akdev.covid19tracker.model;

import com.google.gson.annotations.SerializedName;

public class CovidData {

    @SerializedName("last_updated")
    private String lastUpdated;

    @SerializedName("confirmed")
    private int confirmed;

    @SerializedName("deaths")
    private int deaths;

    @SerializedName("recovered")
    private int recovered;

    @SerializedName(value = "data")
    private Location location;

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}