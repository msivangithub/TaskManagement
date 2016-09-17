package com.mytask.taskmanager.util;

import java.text.DateFormatSymbols;
import java.util.Random;

/**
 * Created by NEWSYSTEM1 on 5/20/2016.
 */
public final class DateUtil {

    private DateUtil() {

    }
    private static DateUtil instance = null;

    public static DateUtil newInstance() {
        if (instance == null) {
            instance = new DateUtil();
        }
        return instance;
    }

    public String getMonthName(int month) {
        String monthName = null;
        DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance();
        String[] months = dateFormatSymbols.getMonths();
        if (month >= 0 && month <= 11) {
            monthName = months[month];
        }
        return monthName;
    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
