package com.akdev.covid19tracker.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Notification extends RealmObject {

    @PrimaryKey
    private long id;
    private String content;
    private String title;
    private String createdDate;

}
