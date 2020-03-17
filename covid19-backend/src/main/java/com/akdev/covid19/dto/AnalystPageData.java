package com.akdev.covid19.dto;

import lombok.Data;

@Data
public class AnalystPageData {

    private String elementId;
    private String url;
    private String chartType;
    private boolean async;

}
