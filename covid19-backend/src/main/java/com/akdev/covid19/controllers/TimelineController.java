package com.akdev.covid19.controllers;

import com.akdev.covid19.services.TimelineParsing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.akdev.covid19.utils.Constant.KEY_HEADER_TITLE;

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
        model.addAttribute(KEY_HEADER_TITLE, "Covid-19 Timeline for major updates (GMT)");
        return "timeline";
    }
}
