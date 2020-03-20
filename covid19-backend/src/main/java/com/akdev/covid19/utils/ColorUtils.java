package com.akdev.covid19.utils;

import java.awt.*;
import java.util.Random;

public class ColorUtils {

    public static Color randomRBG() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return  new Color(r, g, b);
    }

    public static String randomStringRGB() {
        Color c = randomRBG();
        return String.format("rgb(%s,%s,%s)", c.getRed(), c.getGreen(), c.getBlue());
    }

}
