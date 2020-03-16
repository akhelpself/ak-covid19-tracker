package com.akdev.covid19.analyst;

import com.akdev.covid19.model.CovidUser;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MainScript {

    private static final int INDEX_AGE = 7;
    private static final int INDEX_GENDER = 6;
    private static final int INDEX_SYMPTOM_ONSET = 8;
    private static final int INDEX_HOSP_VISIT = 10;
    private static final int INDEX_EXPOSURE_START = 13;
    private static final int INDEX_DEATH = 18;


    @SuppressWarnings("Deprecated")
    public static void main(String[] args) {
        List<CovidUser> dataList = parseData();
        System.out.println("Size: " + dataList.size());

        Map<String, Double> ageGroup = new LinkedHashMap<>();
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
            .forEach(age -> {
                ageGroup.keySet().forEach(k -> {
                    List<Integer> g = Arrays.stream(k.split("-"))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    if ((g.size() == 1 && g.get(0) < age) || (g.size() == 2 && g.get(0) < age && g.get(1) > age)) {
                        double p = (ageGroup.get(k) * s + 1) / s;
                        ageGroup.put(k, p);
                    }
                });
            });

        Map<String, Double> results = new LinkedHashMap<>();
        ageGroup.forEach((k,v) -> results.put(k, 100 * Math.round(v * 10000.0)/10000.0));
        System.out.println(results.toString());
    }

    public static List<CovidUser> parseData() {
        List<CovidUser> results = new ArrayList<>();
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
                } catch (Exception e) {
                    e.printStackTrace(); //error parsing row - ignore it
                }
                results.add(target);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
