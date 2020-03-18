package com.akdev.covid19.services.impl;

import com.akdev.covid19.model.geo.Placemark;
import com.akdev.covid19.services.CovidGeoService;
import jdk.nashorn.internal.ir.SplitReturn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.akdev.covid19.utils.ZipUtils.unzip;
import static org.apache.http.HttpHeaders.DESTINATION;

public class CovidGeoServiceImpl implements CovidGeoService {

    private static final String GOOGLE_MAP = "https://www.google.com/maps/d/u/0/kml?mid=1a04iBi41DznkMaQRnICO40ktROfnMfMx&ll=12.44699010934282%2C-108.16550402097914&z=3";

    public List<Placemark> getListPlaces() throws Exception {

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
            Element e = (Element) nodeList.item(0);
            String name = e.getElementsByTagName("name").item(0).getTextContent();
            String description = e.getElementsByTagName("description").item(0).getTextContent();
            String styleUrl = e.getElementsByTagName("styleUrl").item(0).getTextContent();
            String coordinates = e.getElementsByTagName("Point").item(0).getChildNodes().item(1).getTextContent().replace("\n", "").replace(" ", "");

            Placemark p = new Placemark();
            p.setCoordinates(coordinates);
            p.setDescription(description);
            p.setStyleUrl(styleUrl);
            p.setName(name);

            placemarks.add(p);
        }
        return placemarks;
    }
}
