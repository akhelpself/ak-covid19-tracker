package com.akdev.covid19.services;

import com.akdev.covid19.model.ChartData;

public interface AnalysisService {

    ChartData groupByAge(String type) throws Exception;

    ChartData groupConfirmedByCountry() throws Exception;

    ChartData groupDeathsByCountry() throws Exception;

    ChartData groupRecoveredByCountry() throws Exception;

    ChartData groupConfirmedByGender() throws Exception;

    ChartData groupDeathsByGender() throws Exception;

    ChartData groupConfirmedBySymptom() throws Exception;

    ChartData timeSeriesReport(String type) throws Exception;




}
