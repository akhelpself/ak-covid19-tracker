package com.akdev.covid19.utils;

import com.akdev.covid19.model.Coordinates;
import com.akdev.covid19.model.CovidData;
import com.akdev.covid19.model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class CSVReader {

    private Logger logger = LogManager.getLogger(CSVReader.class);
    private SimpleDateFormat fm = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy");

    public static void main(String[] args) throws Exception {
        CSVReader csvReader = new CSVReader();
        csvReader.convertData();
    }

    public List<CovidData> convertData() throws IOException {
        String link = "https://docs.google.com/spreadsheets/d/e/2PACX-1vR30F8lYP3jG7YOq8es0PBpJIE5yvRVZffOyaqC0GgMBN6yt0Q-NI8pxS7hd1F9dYXnowSC6zpZmW9D/pub?output=csv";
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        InputStream inputStream = httpURLConnection.getInputStream();
        String[] line = getStringFromStream(inputStream).split("\\n");
        List<CovidData> results = new ArrayList<>();
        for (int i = 0; i < line.length - 1; i++) {
            if (i > 5) {
                String[] c = line[i].replaceAll("\\r", "").split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                CovidData data = new CovidData();
                Location location = new Location();
                location.setCountry(c[0]);
                data.setLocation(location);
                data.setConfirmed(parseInt(c[1]));
                data.setDeaths(parseInt(c[2]));
                data.setSerious(parseInt(c[3]));
                data.setCritical(parseInt(c[4]));
                data.setRecovered(parseInt(c[5]));
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

    private static String getStringFromStream(InputStream inputStream) throws IOException {
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