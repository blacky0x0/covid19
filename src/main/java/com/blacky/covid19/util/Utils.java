package com.blacky.covid19.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class Utils {
    private final static LocalTime ZERO_HOUR = LocalTime.of(0, 0, 0);
    private final static LocalTime FIRST_HOUR = LocalTime.of(1, 0, 0);

    public static String format(ZonedDateTime date) {
        return date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static ZonedDateTime convertToZonedDateTime(LocalDate date, LocalTime time) {
        return ZonedDateTime.of(date, time, ZoneOffset.UTC);
    }

    public static String convertWithZeroHourUtc(LocalDate date) {
        return format(convertToZonedDateTime(date, ZERO_HOUR));
    }

    public static String convertWithFirstHourUtc(LocalDate date) {
        return format(convertToZonedDateTime(date, FIRST_HOUR));
    }

}
