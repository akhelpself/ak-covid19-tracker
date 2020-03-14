package com.akdev.covid19.services;

import com.akdev.covid19.model.CovidData;
import com.akdev.covid19.model.LatestData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.akdev.covid19.Route.*;

@Service
public class CoronavirusServiceImpl {

    private CacheManager cacheManager;
    public CoronavirusServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * @return Object containing all real-time Coronavirus statistics.
     * @throws IOException error connecting to api.
     */
    public List<CovidData> getAllData() throws Exception {
        return cacheManager.get(ALL, new TypeReference<List<CovidData>>() {});
    }

    /**
     * @return Object containing just the key real-time Coronavirus statistics.
     * @throws IOException error connecting to api.
     */
    public LatestData getLatestData() throws Exception {
        if (cacheManager.invalid(LATEST)) {
            return cacheManager.get(LATEST, LatestData.class);
        }
        List<CovidData> covidData = getAllData();
        LatestData data = new LatestData();
        data.setDeaths(covidData.stream().mapToInt(CovidData::getDeaths).sum());
        data.setConfirmed(covidData.stream().mapToInt(CovidData::getConfirmed).sum());
        data.setRecovered(covidData.stream().mapToInt(CovidData::getRecovered).sum());
        cacheManager.put(LATEST, new ObjectMapper().writeValueAsString(data));
        return data;
    }

}