package com.akdev.covid19.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "analysis")
public class AnalysisController {

    @GetMapping()
    public String pageDefault() {
        return "analysis/analysis_age";
    }

    @GetMapping(value = "/{type}")
    public String pageWithType(@PathVariable String type) {
        switch (type) {
            case "age":
                return "analysis/analysis_age";
            case "country":
                return "analysis/analysis_country";
            case "gender":
                return "analysis/analysis_gender";
            default:
                return "analysis/analysis_age";
        }
    }

}
