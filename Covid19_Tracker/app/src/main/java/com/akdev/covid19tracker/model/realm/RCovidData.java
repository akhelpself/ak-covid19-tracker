package com.akdev.covid19tracker.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RCovidData extends RealmObject {

    @PrimaryKey
    private long id;
    private String data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
