package com.akdev.covid19.utils;

import org.jsoup.nodes.Element;

public class Spy {

    public static boolean interContextContains(Element dk, String patt) {
        return interContext(dk).contains(patt);
    }

    public static boolean outerContextContains(Element dk, String patt) {
        return outerContext(dk).contains(patt);
    }

    public static String outerContext(Element dk) {
        String res = "";
        Element cur = dk;
        for (int k = 0; k < 6 && cur != null; k++) {
            res = res + cur.tagName() + "#" + cur.className() + "; ";
            cur = cur.parent();
        }
        return res;
    }

    public static String interContext(Element dk) {
        String res = "";
        Element cur = dk;

        for (int k = 0; k < 6 && cur != null; k++) {
            res = res + cur.tagName() + "#" + cur.className() + "; ";
            try {
                cur = cur.child(0);
            } catch (Exception e) {
                break;
            }
        }

        return res;
    }
}