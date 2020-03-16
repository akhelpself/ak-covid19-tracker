package com.akdev.covid19.services;

import com.akdev.covid19.analyst.MainScript;
import com.akdev.covid19.model.ChartData;
import com.akdev.covid19.model.ChartDataSets;
import com.akdev.covid19.model.CovidData;
import com.akdev.covid19.model.CovidUser;
import com.akdev.covid19.utils.Constant;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private Logger logger = LogManager.getLogger(AnalysisServiceImpl.class);

    private static final int INDEX_AGE = 7;
    private static final int INDEX_GENDER = 6;
    private static final int INDEX_SYMPTOM_ONSET = 8;
    private static final int INDEX_HOSP_VISIT = 10;
    private static final int INDEX_EXPOSURE_START = 13;
    private static final int INDEX_DEATH = 18;
    private static final int INDEX_SYMPTOM = 20;

    private static final String CACHE_USER_DATA = "CACHE_USER_DATA";
    private static final String CACHE_DEATH_BY_AGE = "DEATH_BY_AGE";
    private static final String CACHE_CONFIRMED_BY_AGE = "CONFIRMED_BY_AGE";
    private static final String CACHE_CONFIRMED_BY_COUNTRY = "CONFIRMED_BY_COUNTRY";
    private static final String CACHE_DEATHS_BY_COUNTRY = "DEATHS_BY_COUNTRY";
    private static final String CACHE_RECOVERED_BY_COUNTRY = "RECOVERED_BY_COUNTRY";
    private static final String CONFIRMED_BY_GENDER = "CONFIRMED_BY_GENDER";
    private static final String DEATHS_BY_GENDER = "DEATHS_BY_GENDER";
    private static final String CONFIRMED_BY_SYMPTOM = "CONFIRMED_BY_SYMPTOM";

    private CacheManager cacheManager;
    private CoronavirusService coronavirusService;

    public AnalysisServiceImpl(CacheManager cacheManager,
                               CoronavirusService coronavirusService) {
        this.cacheManager = cacheManager;
        this.coronavirusService = coronavirusService;
    }


    @Override
    @SuppressWarnings("Duplicates")
    public ChartData groupConfirmedBySymptom() throws Exception {
        if (cacheManager.invalid(CONFIRMED_BY_SYMPTOM)) {
            return cacheManager.get(CONFIRMED_BY_SYMPTOM, ChartData.class);
        }

        final Map<String, Integer> groupBySymptom = new HashMap<>();
        parseData()
                .stream()
                .forEach(x -> {
                    if (x.getSymptom() != null) {
                        String[] s = x.getSymptom().split(",");
                        Arrays.stream(s).forEach(s1 -> {
                            Integer v = groupBySymptom.getOrDefault(s1.trim(), 0);
                            groupBySymptom.put(s1.trim() , ++v);
                        });
                    }
                });

        Map<String, Integer> group = sortByValue(groupBySymptom, 20);


        ChartDataSets dataSets= new ChartDataSets();
        dataSets.setData(group.values().stream().map(Integer::doubleValue).collect(Collectors.toList()));
        dataSets.setLabel("Confirmed by Symptom");
        dataSets.setBackgroundColor(Constant.COLOR_ORANGE);
        dataSets.setBorderWidth(1);
        ChartData data = new ChartData();
        data.setDatasets(new ChartDataSets[]{dataSets});
        data.setLabels(new ArrayList<>(group.keySet()));
        cacheManager.put(CONFIRMED_BY_SYMPTOM, data);
        return data;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public ChartData groupDeathsByGender() throws Exception {
        if (cacheManager.invalid(DEATHS_BY_GENDER)) {
            return cacheManager.get(DEATHS_BY_GENDER, ChartData.class);
        }

        final Map<String, Integer> group = new HashMap<>();
        parseData()
                .stream()
                .filter(CovidUser::isDeath)
                .forEach(x -> {
                    int v = group.getOrDefault(x.getGender(), 0);
                    if (x.getGender() == null || !x.getGender().equals("male") && !x.getGender().equals("female")) {
                        x.setGender("undefined");
                    }
                    group.put(x.getGender(), ++v);
                });


        ChartDataSets dataSets= new ChartDataSets();
        dataSets.setData(group.values().stream().map(Integer::doubleValue).collect(Collectors.toList()));
        dataSets.setLabel("Deaths by Gender");
        dataSets.setBackgroundColor(new String[]{Constant.COLOR_GREEN, Constant.COLOR_ORANGE});
        dataSets.setBorderWidth(1);
        ChartData data = new ChartData();
        data.setDatasets(new ChartDataSets[]{dataSets});
        data.setLabels(new ArrayList<>(group.keySet()));
        cacheManager.put(DEATHS_BY_GENDER, data);
        return data;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public ChartData groupConfirmedByGender() throws Exception {
        if (cacheManager.invalid(CONFIRMED_BY_GENDER)) {
            return cacheManager.get(CONFIRMED_BY_GENDER, ChartData.class);
        }

        final Map<String, Integer> group = new HashMap<>();
        parseData()
            .forEach(x -> {
                int v = group.getOrDefault(x.getGender(), 0);
                if (x.getGender() == null || !x.getGender().equals("male") && !x.getGender().equals("female")) {
                    x.setGender("undefined");
                }
                group.put(x.getGender(), ++v);
            });


        ChartDataSets dataSets= new ChartDataSets();
        dataSets.setData(group.values().stream().map(Integer::doubleValue).collect(Collectors.toList()));
        dataSets.setLabel("Confirmed by Gender");
        dataSets.setBackgroundColor(new String[]{Constant.COLOR_GREEN, Constant.COLOR_ORANGE});
        dataSets.setBorderWidth(1);
        ChartData data = new ChartData();
        data.setDatasets(new ChartDataSets[]{dataSets});
        data.setLabels(new ArrayList<>(group.keySet()));
        cacheManager.put(CONFIRMED_BY_GENDER, data);
        return data;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public ChartData groupRecoveredByCountry() throws Exception {
        if (cacheManager.invalid(CACHE_RECOVERED_BY_COUNTRY)) {
            return cacheManager.get(CACHE_RECOVERED_BY_COUNTRY, ChartData.class);
        }

        Map<String, Integer> group = coronavirusService.getAllData()
                .stream()
                .collect(Collectors.groupingBy(
                        x -> x.getLocation().getCountry(),
                        Collectors.summingInt(CovidData::getRecovered)
                ));

        group = sortByValue(group, 20);
        ChartDataSets dataSets= new ChartDataSets();
        dataSets.setData(group.values().stream().map(Integer::doubleValue).collect(Collectors.toList()));
        dataSets.setLabel("Recovered by country (Top 20)");
        dataSets.setBackgroundColor(Constant.COLOR_GREEN);
        dataSets.setBorderWidth(1);
        ChartData data = new ChartData();
        data.setDatasets(new ChartDataSets[]{dataSets});
        data.setLabels(new ArrayList<>(group.keySet()));
        cacheManager.put(CACHE_RECOVERED_BY_COUNTRY, data);
        return data;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public ChartData groupDeathsByCountry() throws Exception {
        if (cacheManager.invalid(CACHE_DEATHS_BY_COUNTRY)) {
            return cacheManager.get(CACHE_DEATHS_BY_COUNTRY, ChartData.class);
        }

        Map<String, Integer> group = coronavirusService.getAllData()
                .stream()
                .collect(Collectors.groupingBy(
                        x -> x.getLocation().getCountry(),
                        Collectors.summingInt(CovidData::getDeaths)
                ));

        group = sortByValue(group, 20);
        ChartDataSets dataSets= new ChartDataSets();
        dataSets.setData(group.values().stream().map(Integer::doubleValue).collect(Collectors.toList()));
        dataSets.setLabel("Deaths by country (Top 20)");
        dataSets.setBackgroundColor(Constant.COLOR_RED);
        dataSets.setBorderWidth(1);
        ChartData data = new ChartData();
        data.setDatasets(new ChartDataSets[]{dataSets});
        data.setLabels(new ArrayList<>(group.keySet()));
        cacheManager.put(CACHE_DEATHS_BY_COUNTRY, data);
        return data;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public ChartData groupConfirmedByCountry() throws Exception {
        if (cacheManager.invalid(CACHE_CONFIRMED_BY_COUNTRY)) {
            return cacheManager.get(CACHE_CONFIRMED_BY_COUNTRY, ChartData.class);
        }

        Map<String, Integer> group = coronavirusService.getAllData()
                .stream()
                .collect(Collectors.groupingBy(
                        x -> x.getLocation().getCountry(),
                        Collectors.summingInt(CovidData::getConfirmed)
                ));

        group = sortByValue(group, 20);
        ChartDataSets dataSets= new ChartDataSets();
        dataSets.setData(group.values().stream().map(Integer::doubleValue).collect(Collectors.toList()));
        dataSets.setLabel("Confirmed by country (Top 20)");
        dataSets.setBackgroundColor(Constant.COLOR_ORANGE);
        dataSets.setBorderWidth(1);
        ChartData data = new ChartData();
        data.setDatasets(new ChartDataSets[]{dataSets});
        data.setLabels(new ArrayList<>(group.keySet()));
        cacheManager.put(CACHE_CONFIRMED_BY_COUNTRY, data);
        return data;
    }

    public static Map<String, Integer> sortByValue(Map<String, Integer> hm, int limit) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort(((o1, o2) -> o2.getValue().compareTo(o1.getValue())));

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        int i = 0;
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
            i++;
            if (i == limit) break;
        }
        return temp;
    }



    @Override
    public ChartData groupByAge(String type) throws Exception {
        String keyCache = "";
        List<CovidUser> dataList = new ArrayList<>();
        String backgroundColor = "";
        String label = "";

        switch (type) {
            case "death":
                keyCache = CACHE_DEATH_BY_AGE;
                backgroundColor = Constant.COLOR_RED;
                label = "Age death case (%)";
                break;
            case "confirmed":
                keyCache = CACHE_CONFIRMED_BY_AGE;
                backgroundColor = Constant.COLOR_ORANGE;
                label = "Age confirmed case (%)";
                break;
        }
        Map<String, Double> ageGroup = new LinkedHashMap<>();
        if (cacheManager.invalid(keyCache)) {
            try {
                return cacheManager.get(keyCache, ChartData.class);
            } catch (Exception e) {
                logger.error(e);
            }
        }

        switch (type) {
            case "death":
                dataList = parseData().stream().filter(CovidUser::isDeath).collect(Collectors.toList());
                break;
            case "confirmed":
                dataList = parseData();
                break;
        }

        ageGroup.put("0-10", 0d);
        ageGroup.put("10-20", 0d);
        ageGroup.put("20-30", 0d);
        ageGroup.put("30-40", 0d);
        ageGroup.put("40-50", 0d);
        ageGroup.put("50-60", 0d);
        ageGroup.put("50-70", 0d);
        ageGroup.put("70", 0d);

        int s = dataList.size();
        dataList.stream().mapToInt(CovidUser::getAge)
            .forEach(age ->
                ageGroup.keySet().forEach(k -> {
                    List<Integer> g = Arrays.stream(k.split("-"))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    if ((g.size() == 1 && g.get(0) < age) || (g.size() == 2 && g.get(0) < age && g.get(1) > age)) {
                        double p = (ageGroup.get(k) * s + 1) / s;
                        ageGroup.put(k, p);
                    }
                })
            );

        ChartDataSets dataSets= new ChartDataSets();
        dataSets.setData(
                ageGroup.values().stream().map(x -> x = 100 * Math.round(x * 10000.0) / 10000.0).collect(Collectors.toList())
        );
        dataSets.setLabel(label);
        dataSets.setBorderWidth(1);
        dataSets.setBackgroundColor(backgroundColor);

        ChartData data = new ChartData();
        data.setDatasets(new ChartDataSets[]{dataSets});
        data.setLabels(new ArrayList<>(ageGroup.keySet()));
        cacheManager.put(keyCache, data);
        return data;
    }


    @Scheduled(fixedRate = 3600)
    private List<CovidUser> parseData() {
        List<CovidUser> results = new ArrayList<>();
        if (cacheManager.invalid(CACHE_USER_DATA)) {
            try {
                return cacheManager.get(CACHE_USER_DATA, new TypeReference<List<CovidUser>>() {
                });
            } catch (Exception e) {
                logger.error(e);
            }
        }
        try (InputStream inputStream = MainScript.class.getClassLoader().getResourceAsStream("Kudos_report.xlsx")) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            int i = 0;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                i++;
                if (i < 3) continue;
                CovidUser target = new CovidUser();

                try {
                    if (currentRow.getCell(INDEX_AGE).getCellType() == CellType.NUMERIC) {
                        target.setAge((int) currentRow.getCell(INDEX_AGE).getNumericCellValue());
                    } else continue;

                    if (currentRow.getCell(INDEX_GENDER).getCellType() == CellType.STRING) {
                        target.setGender(currentRow.getCell(INDEX_GENDER).getStringCellValue());
                    } else continue;

                    if (currentRow.getCell(INDEX_SYMPTOM_ONSET).getCellType() == CellType.NUMERIC) {
                        if (HSSFDateUtil.isCellDateFormatted(currentRow.getCell(INDEX_SYMPTOM_ONSET))) {
                            target.setSymptomOnset(currentRow.getCell(INDEX_SYMPTOM_ONSET).getDateCellValue());
                        }
                    }

                    if (currentRow.getCell(INDEX_HOSP_VISIT).getCellType() == CellType.NUMERIC) {
                        if (HSSFDateUtil.isCellDateFormatted(currentRow.getCell(INDEX_HOSP_VISIT))) {
                            target.setSymptomOnset(currentRow.getCell(INDEX_HOSP_VISIT).getDateCellValue());
                        }
                    }

                    if (currentRow.getCell(INDEX_EXPOSURE_START).getCellType() == CellType.NUMERIC) {
                        if (HSSFDateUtil.isCellDateFormatted(currentRow.getCell(INDEX_EXPOSURE_START))) {
                            target.setSymptomOnset(currentRow.getCell(INDEX_EXPOSURE_START).getDateCellValue());
                        }
                    }

                    if (currentRow.getCell(INDEX_DEATH).getCellType() == CellType.NUMERIC) {
                        if (currentRow.getCell(INDEX_DEATH).getNumericCellValue() != 0) target.setDeath(true);
                    }

                    if (currentRow.getCell(INDEX_SYMPTOM) != null && currentRow.getCell(INDEX_SYMPTOM).getCellType() == CellType.STRING) {
                        target.setSymptom(currentRow.getCell(INDEX_SYMPTOM).getStringCellValue());
                    }
                } catch (Exception e) {
                    logger.error(e); //error parsing row - ignore it
                }
                results.add(target);
            }
        } catch (Exception e) {
            logger.error(e);
        }

        cacheManager.put(CACHE_USER_DATA, results);
        return results;
    }
}
