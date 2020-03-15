package com.akdev.covid19tracker.ui.dashboard;

import android.app.Application;

import com.akdev.covid19tracker.R;
import com.akdev.covid19tracker.model.CovidData;
import com.akdev.covid19tracker.model.realm.RCovidData;
import com.akdev.covid19tracker.service.CovidService;
import com.akdev.covid19tracker.service.ServiceAPI;
import com.akdev.covid19tracker.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DashboardViewModel extends AndroidViewModel {

    private MutableLiveData<List<CovidData>> mCovidData;
    private MutableLiveData<String> dialog;

    public DashboardViewModel(Application application) {
        super(application);
        mCovidData = new MutableLiveData<>();
        dialog = new MutableLiveData<>();

        Realm realm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build());
        RCovidData rCovidData = realm.where(RCovidData.class).equalTo("id",  1).findFirst();
        if (rCovidData != null) {
            Gson g = new Gson();
            List<CovidData> items = g.fromJson(rCovidData.getData(), new TypeToken<List<CovidData>>(){}.getType());
            mCovidData.postValue(items);
        }

        if (!CommonUtils.checkNetworkWithAlert(application)) return;

        CovidService.getEntity(ServiceAPI.GET_ALL, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() == 200) {
                    Gson g = new Gson();
                    String body = response.body().string();
                    List<CovidData> items = g.fromJson(body, new TypeToken<List<CovidData>>(){}.getType());
                    mCovidData.postValue(items);
                    Realm realm = Realm.getInstance(new RealmConfiguration.Builder()
                            .deleteRealmIfMigrationNeeded()
                            .build());

                    realm.executeTransaction(r -> {
                        RCovidData rCovidData = new RCovidData();
                        rCovidData.setId(1);
                        rCovidData.setData(body);
                        r.insertOrUpdate(rCovidData);
                    });
                } else {
                    dialog.postValue(application.getString(R.string.dialog_cannot_get_data));
                }
            }
        });
    }

    public MutableLiveData<List<CovidData>> getCovidData() {
        return mCovidData;
    }

    public MutableLiveData<String> getDialog() {
        return dialog;
    }
}