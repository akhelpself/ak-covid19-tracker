package com.akdev.covid19.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "map")
public class MapController {

    @GetMapping
    public String getMap() {
        return "map";
    }
}
