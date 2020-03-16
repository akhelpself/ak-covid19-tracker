package com.akdev.covid19tracker.ui.home;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.util.Log;

import com.akdev.covid19tracker.R;
import com.akdev.covid19tracker.model.LatestData;
import com.akdev.covid19tracker.service.CovidService;
import com.akdev.covid19tracker.service.ServiceAPI;
import com.akdev.covid19tracker.utils.CommonUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<LatestData> latestData;
    private MutableLiveData<String> dialog;

    private final String TAG = HomeViewModel.class.getSimpleName();


    public HomeViewModel(Application application) {
        super(application);
        Realm realm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build());

        latestData = new MutableLiveData<>();
        dialog = new MutableLiveData<>();

        LatestData data = realm.where(LatestData.class).equalTo("id", 1).findFirst();
        if (data != null) {
            latestData.postValue(data);
        }

        if (!CommonUtils.checkNetworkWithAlert(application)) return;

        try {
            CovidService.getEntity(ServiceAPI.GET_LATEST, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    dialog.postValue(e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.code() == 200) {
                        String body = response.body().string();
                        Gson g = new Gson();
                        LatestData data = g.fromJson(body, LatestData.class);
                        latestData.postValue(data);
                        Realm realm = Realm.getInstance(new RealmConfiguration.Builder()
                                .deleteRealmIfMigrationNeeded()
                                .build());
                        realm.executeTransaction(r -> {
                            data.setId(1);
                            r.insertOrUpdate(data);
                        });
                    } else {
                        dialog.postValue(application.getString(R.string.dialog_cannot_get_data));
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Latest exception:", e.getMessage());
        }
    }

    public MutableLiveData<LatestData> getLatestData() {
        return latestData;
    }

    public MutableLiveData<String> getDialog() {
        return dialog;
    }
}