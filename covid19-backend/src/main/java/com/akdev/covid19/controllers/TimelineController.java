package com.akdev.covid19.controllers;

import com.akdev.covid19.services.TimelineParsing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "timeline")
public class TimelineController {

    private TimelineParsing timelineParsing;

    public TimelineController(TimelineParsing timelineParsing) {
        this.timelineParsing = timelineParsing;
    }

    @GetMapping
    public String timeline(Model model) throws Exception {
        model.addAttribute("timeline", timelineParsing.getData());
        return "timeline";
    }
}
