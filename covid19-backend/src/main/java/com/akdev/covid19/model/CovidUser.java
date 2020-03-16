package com.akdev.covid19.model;

import lombok.Data;

import java.util.Date;

@Data
public class CovidUser {

    private int age;
    private String gender;
    private Date symptomOnset;
    private Date hospVisitDate;
    private Date exposureStart;
    private Date reportDate;
    private boolean death;

}
