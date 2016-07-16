package com.task.mytaskmanager.util;

import java.text.DateFormatSymbols;

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
}
