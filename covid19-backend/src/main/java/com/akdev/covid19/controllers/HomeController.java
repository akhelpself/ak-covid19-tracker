package com.akdev.covid19.controllers;

import com.akdev.covid19.model.GeoIP;
import com.akdev.covid19.services.CoronavirusService;
import com.akdev.covid19.services.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping
public class HomeController {


    private CoronavirusService coronavirusService;
    private NotificationService notificationService;

    public HomeController(CoronavirusService coronavirusService,
                          NotificationService notificationService) {
        this.coronavirusService = coronavirusService;
        this.notificationService = notificationService;
    }


    @GetMapping
    public String home(Model model,
                       HttpSession session) throws Exception {
        model.addAttribute("latestData", coronavirusService.getLatestData());

        GeoIP geoIP = (GeoIP) session.getAttribute("location");
        model.addAttribute("location", geoIP);
        model.addAttribute("notification", notificationService.detectLocationSafety(geoIP));

        return "home";
    }


}
