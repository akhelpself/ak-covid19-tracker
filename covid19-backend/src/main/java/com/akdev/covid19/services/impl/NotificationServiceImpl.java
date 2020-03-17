package com.akdev.covid19.services.impl;

import com.akdev.covid19.model.CovidData;
import com.akdev.covid19.model.GeoIP;
import com.akdev.covid19.model.Notification;
import com.akdev.covid19.services.CoronavirusService;
import com.akdev.covid19.services.NotificationService;
import com.akdev.covid19.utils.MapUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    private CoronavirusService coronavirusService;

    public NotificationServiceImpl(CoronavirusService coronavirusService) {
        this.coronavirusService = coronavirusService;
    }

    @Override
    public Notification detectLocationSafety(GeoIP geoIP) throws Exception {
        List<CovidData> covidDataList = coronavirusService.getAllData();
        Map<String, Double> network = new HashMap<>();
        covidDataList.parallelStream().forEach(x -> {
            double d = distance(
                    Double.parseDouble(geoIP.getLatitude()),
                    Double.parseDouble(geoIP.getLongitude()),
                    Double.parseDouble(x.getLocation().getCoordinates().getLat()),
                    Double.parseDouble(x.getLocation().getCoordinates().getLng()), "K");
            network.put(x.getLocation().toString(), d);
        });

        Notification notification = new Notification();
        Map<String, Double> results = MapUtils.sortDoubleByValue(network, 3, 0);
        int i = 0;
        for (String k: results.keySet()) {
            double v = results.get(k);
            if (i == 0) {
                if (v < 100) notification.setLevel(3);
                else if (v > 100 && v < 200) notification.setLevel(2);
                else notification.setLevel(1);
                notification.setAddress(geoIP.getAddress());
                notification.setDistance( Math.round(v * 100.0) / 100.0);
                notification.setNeatestLocation(k);
            } else {
                notification.getRelateLocation().put(k, v);
            }
            i++;
        }
        return notification;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}
