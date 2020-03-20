package com.akdev.covid19.services.impl;

import com.akdev.covid19.model.CovidData;
import com.akdev.covid19.model.LatestData;
import com.akdev.covid19.model.geo.Placemark;
import com.akdev.covid19.services.CoronavirusService;
import com.akdev.covid19.utils.CSVReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.akdev.covid19.utils.Constant.DESTINATION;
import static com.akdev.covid19.utils.Route.*;
import static com.akdev.covid19.utils.ZipUtils.unzip;

@Service
public class CoronavirusServiceImpl implements CoronavirusService {

    private CacheManager cacheManager;
    private static final String GOOGLE_MAP = "https://www.google.com/maps/d/u/0/kml?mid=1a04iBi41DznkMaQRnICO40ktROfnMfMx&ll=12.44699010934282%2C-108.16550402097914&z=3";
    private static final String CACHE_VIRUS_PLACE = "CACHE_VIRUS_PLACE";
    private CSVReader csvReader;
    private Logger logger = LogManager.getLogger(CoronavirusServiceImpl.class);

    public CoronavirusServiceImpl(CacheManager cacheManager,
                                  CSVReader csvReader) {
        this.cacheManager = cacheManager;
        this.csvReader = csvReader;
    }

    /**
     * @return Object containing all real-time Coronavirus statistics.
     * @throws IOException error connecting to api.
     */
    @Override
    public List<CovidData> getAllData() throws Exception {
        if (cacheManager.invalid(ALL)) {
            return cacheManager.get(ALL, new TypeReference<List<CovidData>>() {});
        }
        List<CovidData> covidDataList = csvReader.reportTimeSeries();
        cacheManager.put(ALL, covidDataList);
        return covidDataList;
    }

    /**
     * @return Object containing just the key real-time Coronavirus statistics.
     * @throws IOException error connecting to api.
     */
    @Override
    public LatestData getLatestData() throws Exception {
        if (cacheManager.invalid(LATEST)) {
            return cacheManager.get(LATEST, LatestData.class);
        }
        List<CovidData> covidData = getAllData();
        LatestData data = new LatestData();
        data.setDeaths(covidData.stream().mapToInt(CovidData::getDeaths).sum());
        data.setConfirmed(covidData.stream().mapToInt(CovidData::getConfirmed).sum());
        data.setRecovered(covidData.stream().mapToInt(CovidData::getRecovered).sum());
        cacheManager.put(LATEST, new ObjectMapper().writeValueAsString(data));
        return data;
    }

    @Override
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public List<Placemark> getPlacesFromGoogleMap() throws Exception {
        if (cacheManager.invalid(CACHE_VIRUS_PLACE)) {
            return cacheManager.get(CACHE_VIRUS_PLACE, new TypeReference<List<Placemark>>() {});
        }
        InputStream inputStream = new URL(GOOGLE_MAP).openStream();
        unzip(inputStream, DESTINATION);
        File xmlFile = new File(DESTINATION, "doc.kml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getElementsByTagName("Document").item(0);

        NodeList folders = ((Element) doc.getElementsByTagName("Document").item(0)).getElementsByTagName("Folder");
        NodeList nodeList = ((Element) folders.item(0)).getElementsByTagName("Placemark");
        List<Placemark> placemarks = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            try {
                Element e = (Element) nodeList.item(i);
                String name = e.getElementsByTagName("name").item(0).getTextContent();
                logger.info("Parsing city: [{}]", name);
                String description = e.getElementsByTagName("description").item(0).getTextContent();
                String styleUrl = e.getElementsByTagName("styleUrl").item(0).getTextContent();
                String coordinates = e.getElementsByTagName("Point").item(0).getChildNodes().item(1).getTextContent().replace("\n", "").replace(" ", "");

                Placemark p = new Placemark();
                p.setCoordinates(coordinates);
                p.setDescription(description);
                p.setStyleUrl(styleUrl);
                p.setName(name);

                placemarks.add(p);
            } catch (Exception e) {
                logger.error(e);
            }

        }
        cacheManager.put(CACHE_VIRUS_PLACE, placemarks);
        return placemarks;
    }
}