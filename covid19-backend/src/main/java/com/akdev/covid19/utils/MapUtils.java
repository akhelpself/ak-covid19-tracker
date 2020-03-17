package com.akdev.covid19.utils;

import java.util.*;

public class MapUtils {

    @SuppressWarnings("Duplicates")
    public static Map<String, Integer> sortIntByValue(Map<String, Integer> hm, int limit) {
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

    @SuppressWarnings("Duplicates")
    public static Map<String, Double> sortDoubleByValue(Map<String, Double> hm, int limit, int t) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list = new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort(((o1, o2) -> t == 1 ?
                o2.getValue().compareTo(o1.getValue())
                : o1.getValue().compareTo(o2.getValue())));

        HashMap<String, Double> temp = new LinkedHashMap<>();
        int i = 0;
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
            i++;
            if (i == limit) break;
        }
        return temp;
    }


}
