package com.akdev.covid19.controllers;

import com.akdev.covid19.model.CovidData;
import com.akdev.covid19.model.LatestData;
import com.akdev.covid19.services.CoronavirusServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
