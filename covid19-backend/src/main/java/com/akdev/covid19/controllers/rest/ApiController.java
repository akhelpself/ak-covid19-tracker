package com.akdev.covid19.controllers.rest;

import com.akdev.covid19.model.CovidData;
import com.akdev.covid19.model.GeoIP;
import com.akdev.covid19.model.LatestData;
import com.akdev.covid19.model.Notification;
import com.akdev.covid19.services.impl.CoronavirusServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ApiController {

    private CoronavirusServiceImpl coronavirusService;

    public ApiController(CoronavirusServiceImpl coronavirusService) {
        this.coronavirusService = coronavirusService;
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<CovidData>> getAll() throws Exception {
        return ResponseEntity.ok(coronavirusService.getAllData());
    }

    @GetMapping(value = "latest")
    public ResponseEntity<LatestData> getLatest() throws Exception {
        return ResponseEntity.ok(coronavirusService.getLatestData());
    }

    @GetMapping(value = "location")
    public void changeLocation(
            @RequestParam(value = "country", required = false, defaultValue = "en") String country,
            @RequestParam(value = "code", required = false, defaultValue = "en") String code,
            @RequestParam(value = "city", required = false, defaultValue = "") String city,
            @RequestParam(value = "lat", required = false, defaultValue = "lat") float lat,
            @RequestParam(value = "lng", required = false, defaultValue = "lng") float lng,
            HttpSession httpSession) {
        GeoIP geoIP = new GeoIP(country, code, null, city, String.valueOf(lat), String.valueOf(lng));
        httpSession.setAttribute("location", geoIP);
    }

}
