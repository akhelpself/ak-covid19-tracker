package com.akdev.covid19tracker.service;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CovidService {

    public static void getEntity(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
