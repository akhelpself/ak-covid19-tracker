package com.akdev.covid19.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "analysis")
public class AnalysisController {

    @GetMapping
    public String page() {
        return "analysis";
    }
}
