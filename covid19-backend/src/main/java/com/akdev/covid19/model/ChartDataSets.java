package com.akdev.covid19.model;

import lombok.Data;

import java.util.List;

@Data
public class ChartDataSets {

    private String label;
    private List<Double> data;
    private int borderWidth;
    private Object backgroundColor;
    private Object borderColor;
    private boolean fill;
}
