package com.akdev.covid19tracker.ui.dashboard;

import com.akdev.covid19tracker.model.CovidData;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {


    private MutableLiveData<List<CovidData>> mCovidData;

    public DashboardViewModel() {
        mCovidData = new MutableLiveData<>();
        List<CovidData> items = new ArrayList<>();
        items.add(new CovidData());
        items.add(new CovidData());
        items.add(new CovidData());
        items.add(new CovidData());
        items.add(new CovidData());
        items.add(new CovidData());
        items.add(new CovidData());
        items.add(new CovidData());
        items.add(new CovidData());
        items.add(new CovidData());
        items.add(new CovidData());
        mCovidData.setValue(items);
    }

    public MutableLiveData<List<CovidData>> getCovidData() {
        return mCovidData;
    }
}