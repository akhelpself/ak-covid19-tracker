package com.akdev.covid19.controllers;

import com.akdev.covid19.services.CoronavirusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {


    private CoronavirusService coronavirusService;

    public HomeController(CoronavirusService coronavirusService) {
        this.coronavirusService = coronavirusService;
    }


    @GetMapping
    public String home(Model model) throws Exception {
        model.addAttribute("latestData", coronavirusService.getLatestData());
        return "home";
    }


}
