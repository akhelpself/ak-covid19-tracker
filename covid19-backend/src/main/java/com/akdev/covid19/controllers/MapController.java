package com.akdev.covid19.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "map")
public class MapController {

    @GetMapping
    public String getMap(
            @RequestParam(value = "ll", required = false, defaultValue = "") String location,
            Model model
    ) {
        if (StringUtils.isNotBlank(location)) model.addAttribute("location", location);
        return "map";
    }
}
