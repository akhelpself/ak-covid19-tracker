package com.akdev.covid19.controllers;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import static com.akdev.covid19.utils.Constant.*;

@ControllerAdvice
public class CoreController extends BaseController {

    private static final String DEFAULT_HEADER_TITLE = "AK Covid19 Tracker: Coronavirus tracker and update latest information";
    private static final String DEFAULT_HEADER_THUMBNAIL = "/images/cover.png";
    private static final String DEFAULT_DESCRIPTION = "AK Covid19 Tracker: Coronavirus free application to track and update latest information";
    private static final String DEFAULT_DEFAULT_URL = "https://akcovid.com";
    private static final String DEFAULT_HEADER_KEYWORDS = "covid19, coronavirus tracker, covid19 update";

    private static final String ERROR_CODE = "errorCode";
    private static final String HAS_ERROR = "hasError";
    private static final String MESSAGE = "message";
    @ModelAttribute
    public void redirectError(Model model,
                              ModelMap map,
                              @RequestParam(value = "error", required = false, defaultValue = "0") int error) {
        if ("1".equals(map.get(ERROR_CODE))) {
                model.addAttribute(MESSAGE, map.get("message"));
            model.addAttribute(ERROR_CODE, map.get(ERROR_CODE));
            model.addAttribute(HAS_ERROR, true);
            return;
        } else {
            model.addAttribute(HAS_ERROR, false);
        }

        if (error != 0) {
            model.addAttribute(HAS_ERROR, true);
            model.addAttribute(MESSAGE, "Invalid username or password!");
        } else {
            model.addAttribute(HAS_ERROR, false);
        }
    }

    @ModelAttribute
    public void metadata(Model model) {
        model.addAttribute(KEY_HEADER_TITLE, DEFAULT_HEADER_TITLE);
        model.addAttribute(KEY_HEADER_THUMBNAIL_URL, DEFAULT_HEADER_THUMBNAIL);
        model.addAttribute(KEY_HEADER_DESCRIPTION, DEFAULT_DESCRIPTION);
        model.addAttribute(KEY_HEADER_URL, DEFAULT_DEFAULT_URL);
        model.addAttribute(KEY_HEADER_KEYWORDS, DEFAULT_HEADER_KEYWORDS);
    }
}
