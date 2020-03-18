package com.akdev.covid19.services;

import com.akdev.covid19.model.Timeline;

import java.util.List;

public interface TimelineParsing {

    List<Timeline> getData() throws Exception ;
}
