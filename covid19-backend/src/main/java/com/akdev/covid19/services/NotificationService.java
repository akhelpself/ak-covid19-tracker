package com.akdev.covid19.services;

import com.akdev.covid19.model.GeoIP;
import com.akdev.covid19.model.Notification;


public interface NotificationService {

    Notification detectLocationSafety(GeoIP geoIP) throws Exception;

}
