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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class CSVReader {

    private Logger logger = LogManager.getLogger(CSVReader.class);
    private SimpleDateFormat fm = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy");

    public List<CovidData> convertData(String date) throws IOException {
        logger.info("Request service: {}", fm.format(new Date()));

        String link = String.format("https://raw.githubusercontent.com/CSSEGISandData/COVID-19//master/csse_covid_19_data/csse_covid_19_daily_reports/%s.csv", date);
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        InputStream inputStream = httpURLConnection.getInputStream();
        String[] line = getStringFromStream(inputStream).split("\\n");
        List<CovidData> results = new ArrayList<>();
        Arrays.asList(line).forEach(l -> {
            String[] c = l.replaceAll("\\r", "").split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (!c[0].contains("Province/State")) {

                CovidData data = new CovidData();
                Location location = new Location();
                location.setProvince(c[0]);
                location.setCountry(c[1]);
                location.setCoordinates(new Coordinates(c[6], c[7]));
                data.setLocation(location);
                data.setConfirmed(Integer.parseInt(c[3]));
                data.setDeaths(Integer.parseInt(c[4]));
                data.setRecovered(Integer.parseInt(c[5]));
                data.setLastUpdated(c[2]);
                results.add(data);
            }
        });
        return results;
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