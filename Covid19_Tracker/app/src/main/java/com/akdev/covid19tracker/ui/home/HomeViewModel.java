package com.akdev.covid19tracker.ui.home;

import android.app.Application;
import android.util.Log;

import com.akdev.covid19tracker.model.LatestData;
import com.akdev.covid19tracker.service.CovidService;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<LatestData> latestData;
    private final String TAG = HomeViewModel.class.getSimpleName();

    public HomeViewModel(Application application) {
        super(application);
        latestData = new MutableLiveData<>();
        try {
            getLatest(CovidService.GET_LATEST, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String body = response.body().string();
                    Gson g = new Gson();
                    latestData.postValue(g.fromJson(body, LatestData.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("Latest exception:", e.getMessage());
        }
    }

    private void getLatest(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public MutableLiveData<LatestData> getLatestData() {
        return latestData;
    }
}