package com.gbsrnov.caditassessment2weatherapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String formatDate(String utcDate) {
        if (utcDate == null || utcDate.isEmpty()) return "null";

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US).parse(utcDate);
            return new SimpleDateFormat("dd MMM yyyy", Locale.US).format(date);
        } catch (Exception e) {
            return "Invalid Date";
        }
    }
}

