package com.akdev.covid19tracker.service;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ServiceAPI {

    private static final String BASE_URL = "https://covid19.akdownloader.com";
    public static final String GET_LATEST = BASE_URL + "/latest";
    public static final String GET_ALL = BASE_URL + "/all";

}
