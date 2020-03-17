package com.akdev.covid19.services.impl;

import com.akdev.covid19.model.GeoIP;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
public class RawDBGeoIPLocationService {

    private DatabaseReader dbReader;
     
    public RawDBGeoIPLocationService() throws IOException {
        Resource resource = new ClassPathResource("GeoLite2-City.mmdb");
        dbReader = new DatabaseReader.Builder(resource.getInputStream()).build();
    }

    @Cacheable("location")
    public GeoIP getLocation(String ip)
      throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);
        String cityName = response.getCity().getName();
        String countryName = response.getCountry().getName();
        String countryCode = response.getCountry().getIsoCode();
        String latitude = response.getLocation().getLatitude().toString();
        String longitude = response.getLocation().getLongitude().toString();
        return new GeoIP(countryName, countryCode, ip, cityName, latitude, longitude);
    }
}