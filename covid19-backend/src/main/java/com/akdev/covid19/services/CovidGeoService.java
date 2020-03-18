package com.akdev.covid19.services;

import com.akdev.covid19.model.geo.Placemark;

import java.util.List;

public interface CovidGeoService {

    List<Placemark> getListPlaces() throws Exception;

}
