package com.akdev.covid19.utils;

import com.akdev.covid19.model.CovidData;
import com.akdev.covid19.model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CSVReader {

    private Logger logger = LogManager.getLogger(CSVReader.class);
    private SimpleDateFormat fm = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy");

    public static void main(String[] args) throws Exception {

    }

    public static String[] reportConfirmedCasesSeries() throws IOException {
        Map<String, List<String>> results = new HashMap<>();
        String url  = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
        return getStringFromStream(url).split("\\n");
    }

    public List<CovidData> reportTimeSeries() throws IOException {
        String url = "https://docs.google.com/spreadsheets/d/e/2PACX-1vR30F8lYP3jG7YOq8es0PBpJIE5yvRVZffOyaqC0GgMBN6yt0Q-NI8pxS7hd1F9dYXnowSC6zpZmW9D/pub?output=csv";
        String[] line = getStringFromStream(url).split("\\n");
        List<CovidData> results = new ArrayList<>();
        for (int i = 0; i < line.length; i++) {
            if (i > 5) {

                String[] c = line[i].replaceAll("\\r", "").split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                CovidData data = new CovidData();
                Location location = new Location();
                location.setCountry(c[0]);

                if (c[0] != null && c[0].equalsIgnoreCase("total")) continue;

                data.setLocation(location);
                data.setConfirmed(parseInt(c[1]));
                data.setNewConfirmed(parseInt(c[2]));
                data.setDeaths(parseInt(c[3]));
                data.setNewDeaths(parseInt(c[4]));
                data.setSerious(parseInt(c[6]));
                data.setPercentOfDeaths(c[5]);
                data.setRecovered(parseInt(c[7]));
                results.add(data);
            }
        }
        return results;
    }


    private Integer parseInt(String v) {
        try {
            return Integer.parseInt(v.replaceAll("[^\\d.]",""));
        } catch (Exception e) {

        }
        return 0;
    }

    private static String getStringFromStream(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        if (inputStream != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[2048];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                int counter;
                while ((counter = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, counter);
                }
            } finally {
                inputStream.close();
            }
            return writer.toString();
        } else {
            return "No Contents";
        }
    }

}