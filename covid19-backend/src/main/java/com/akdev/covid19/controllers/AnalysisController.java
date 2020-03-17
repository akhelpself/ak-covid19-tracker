package com.akdev.covid19.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.akdev.covid19.utils.Constant.*;

@Controller
@RequestMapping(value = "analysis")
public class AnalysisController {

    @GetMapping()
    public String pageDefault() {
        return "analysis/analysis_age";
    }

    @GetMapping(value = "/{type}")
    public String pageWithType(@PathVariable String type, Model model) {
        model.addAttribute(KEY_HEADER_THUMBNAIL_URL, DEFAULT_DEFAULT_URL + "/images/analysis_cover.png");
        model.addAttribute(KEY_HEADER_TITLE, String.format("AKCovid-19 Tracker: Analysis by %s", type));

        switch (type) {
            case "age":
                return "analysis/analysis_age";
            case "country":
                return "analysis/analysis_country";
            case "gender":
                return "analysis/analysis_gender";
            case "symptom":
                return "analysis/analysis_symptom";
            default:
                return "analysis/analysis_age";
        }
    }

}
