package com.akdev.covid19.controllers;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class BaseController {

    protected void redirectError(RedirectAttributes attributes, String errorCode, String message) {
        attributes.addFlashAttribute("errorCode", errorCode);
        attributes.addFlashAttribute("message", message);
    }

    protected void redirectError(RedirectAttributes attributes, String message) {
        redirectError(attributes, "1", message);
    }

}
