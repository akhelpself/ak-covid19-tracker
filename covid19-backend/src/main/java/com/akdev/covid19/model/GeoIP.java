package com.akdev.covid19.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;

@Data
public class GeoIP {

    private String country;
    private String code;
    private String ipAddress;
    private String city;
    private String latitude;
    private String longitude;
    private int type;

    public GeoIP() {}

    public GeoIP(String country, String code, String ipAddress, String city, String latitude, String longitude) {
        this.ipAddress = ipAddress;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.code = code;
    }

    public String getAddress() {
        String address = "";
        if (StringUtils.isNotBlank(this.city)) {
            address = this.city + ", " + this.country;
        } else {
            address = this.country;
        }

        try {
            return URLDecoder.decode(address, "UTF-8");
        } catch (Exception e) {

        }
        return address;
    }

    public String getCode() {
        if ("KR".equals(this.code) || "CN".equals(this.code) || "JP".equals(this.code) || "VN".equals(this.code)) return this.code;
        if (type == 1) return "US";
        return this.code == null ? "US" : this.code;
    }

    public String getCode2() {
        return this.code;
    }
}