package com.akdev.covid19.controllers;

import com.akdev.covid19.model.GeoIP;
import com.akdev.covid19.model.LatestData;
import com.akdev.covid19.services.CoronavirusService;
import com.akdev.covid19.services.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import static com.akdev.covid19.utils.Constant.*;

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

        LatestData latestData = coronavirusService.getLatestData();
        DecimalFormat df = new DecimalFormat("###,###,###");
        model.addAttribute(KEY_HEADER_TITLE, String.format("AKCovid-19: Latest updated: Confirmed: %s, Deaths: %s, Recovered: %s",
                df.format(latestData.getConfirmed()),
                df.format(latestData.getDeaths()),
                df.format(latestData.getRecovered())));

        model.addAttribute(KEY_HEADER_THUMBNAIL_URL, DEFAULT_DEFAULT_URL + "/images/home_cover.png");

        model.addAttribute("latestData", latestData);

        GeoIP geoIP = (GeoIP) session.getAttribute("location");
        model.addAttribute("location", geoIP);
        model.addAttribute("notification", notificationService.detectLocationSafety(geoIP));

        return "home";
    }


}
