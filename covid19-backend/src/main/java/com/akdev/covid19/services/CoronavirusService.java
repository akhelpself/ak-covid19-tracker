package com.akdev.covid19.services;

import com.akdev.covid19.model.CovidData;
import com.akdev.covid19.model.LatestData;

import java.util.List;

public interface CoronavirusService {

    List<CovidData> getAllData() throws Exception;

    LatestData getLatestData() throws Exception;

}
