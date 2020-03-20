package com.akdev.covid19.controllers.rest;

import com.akdev.covid19.services.AnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiAnalysisController {

    private AnalysisService analysisService;

    private ApiAnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping(value = "/death-by-age")
    public ResponseEntity deathByAge() throws Exception {
        return ResponseEntity.ok(analysisService.groupByAge("death"));
    }

    @GetMapping(value = "/confirmed-by-age")
    public ResponseEntity confirmedByAge() throws Exception {
        return ResponseEntity.ok(analysisService.groupByAge("confirmed"));
    }

    @GetMapping(value = "/confirmed-by-country")
    public ResponseEntity confirmedByCountry() throws Exception {
        return ResponseEntity.ok(analysisService.groupConfirmedByCountry());
    }

    @GetMapping(value = "/deaths-by-country")
    public ResponseEntity deathsByCountry() throws Exception {
        return ResponseEntity.ok(analysisService.groupDeathsByCountry());
    }

    @GetMapping(value = "/recovered-by-country")
    public ResponseEntity recoveredByCountry() throws Exception {
        return ResponseEntity.ok(analysisService.groupRecoveredByCountry());
    }

    @GetMapping(value = "/confirmed-by-gender")
    public ResponseEntity confirmedByGender() throws Exception {
        return ResponseEntity.ok(analysisService.groupConfirmedByGender());
    }

    @GetMapping(value = "/deaths-by-gender")
    public ResponseEntity deathsByGender() throws Exception {
        return ResponseEntity.ok(analysisService.groupDeathsByGender());
    }

    @GetMapping(value = "/confirmed-by-symptom")
    public ResponseEntity confirmedBySymptom() throws Exception {
        return ResponseEntity.ok(analysisService.groupConfirmedBySymptom());
    }


    @GetMapping(value = "/confirmed-by-time-series")
    public ResponseEntity confirmedByTimeSeries() throws Exception {
        return ResponseEntity.ok(analysisService.timeSeriesReport());
    }


}
