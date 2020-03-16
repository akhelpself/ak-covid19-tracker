package com.akdev.covid19.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class StaticController {

    @GetMapping(value = "privacy-policy")
    public String privacyPolicy() {
        return "content/privacy_policy";
    }

    @GetMapping(value = "terms")
    public String terms() {
        return "content/term_of_service";
    }

    @GetMapping(value = "about-us")
    public String aboutUs() {
        return "content/about_us";
    }


    @GetMapping(value = "social")
    public String social() {
        return "under_constructor";
    }

    @GetMapping(value = "new-releases")
    public String newReleases() {
        return "content/new_release";
    }

    @GetMapping(value = "tips")
    public String tips() {
        return "under_constructor";
    }

    @GetMapping("dmca")
    public String dmca() {
        return "content/dmca";
    }

    @GetMapping("cookie-policy")
    public String cookiePolicy() {
        return "content/cookie_policy";
    }



}
