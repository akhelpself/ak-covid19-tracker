package com.akdev.covid19.services.impl;

import com.akdev.covid19.utils.CSVReader;
import com.akdev.covid19.model.CovidData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CacheManager {

    private Logger logger = LogManager.getLogger(CacheManager.class);

    private Map<String, String> d = new HashMap<>();
    private Map<String, Long> t = new HashMap<>(); //exp time

    private ObjectMapper mapper = new ObjectMapper();

    public CacheManager() { }

    public <T> T get(String key, Class<T> cl) throws Exception {
        return mapper.readValue(d.get(key), cl);
    }

    public <T> T get(String key, TypeReference<T> type) throws Exception {
        return mapper.readValue(d.get(key), type);
    }

    public void put(String k, String v) {
        d.put(k, v);
        t.put(k, System.currentTimeMillis());
    }

    public void put(String k, Object v) {
        try {
            put(k, mapper.writeValueAsString(v));
        } catch (Exception e) {
            logger.error("put exception: {}", e);
        }
    }

    public boolean invalid(String key) {
        return t.containsKey(key) && expiredTimeValid(t.get(key));
    }

    private boolean expiredTimeValid(Long expiredTime) {
        return System.currentTimeMillis() - expiredTime < 30 * 60 * 1000;
    }


}
