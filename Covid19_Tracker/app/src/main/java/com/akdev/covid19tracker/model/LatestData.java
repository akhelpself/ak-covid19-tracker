package com.akdev.covid19tracker.model;

import com.google.gson.annotations.SerializedName;

public class LatestData {

    @SerializedName("confirmed")
    private int confirmed;

    @SerializedName("deaths")
    private int deaths;

    @SerializedName("recovered")
    private int recovered;

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
}