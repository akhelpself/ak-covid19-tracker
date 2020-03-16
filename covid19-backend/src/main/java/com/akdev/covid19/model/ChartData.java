package com.akdev.covid19.model;

import lombok.Data;

import java.util.List;

@Data
public class ChartData {

    private List<String> labels;
    private ChartDataSets[] datasets;
}
