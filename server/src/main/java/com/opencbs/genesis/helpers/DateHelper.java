package com.opencbs.genesis.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Makhsut Islamov on 13.12.2016.
 */
public class DateHelper {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm";

    public static Date convert(String value){
        return convert(value, DATE_FORMAT);
    }

    public static Date convert(String value, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateToDate(LocalDate.parse(value, formatter));
    }

    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static Date addMonth(Date date, int months) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime result = localDateTime.plusMonths(months);
        return localDateTimeToDate(result);
    }

    public static Date addMinutes(Date date, Integer minutes) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime result = localDateTime.plusMinutes(minutes);
        return localDateTimeToDate(result);
    }

    public static Date addDays(Date date, Integer days) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime result = localDateTime.plusDays(days);
        return localDateTimeToDate(result);
    }

    public static Date getStartOfMonth(Date date){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        ValueRange range = localDateTime.range(ChronoField.DAY_OF_MONTH);
        Long min = range.getMinimum();
        LocalDateTime endOfMonth= localDateTime.withDayOfMonth(min.intValue());
        return localDateTimeToDate(endOfMonth);
    }

    public static Date getEndOfMonth(Date date){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        ValueRange range = localDateTime.range(ChronoField.DAY_OF_MONTH);
        Long max = range.getMaximum();
        LocalDateTime endOfMonth= localDateTime.withDayOfMonth(max.intValue());
        return localDateTimeToDate(endOfMonth);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    private static String toUTC(Date date, String format){
        DateFormat gmtFormat = new SimpleDateFormat(format);
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        gmtFormat.setTimeZone(gmtTime);
        return gmtFormat.format(date);
    }

    public static String toUTCDateTime(Date date) {
       return toUTC(date,DATETIME_FORMAT);
    }

    public static String toUTCTime(Date date) {
        return toUTC(date,TIME_FORMAT);
    }

    public static String toUTCDate(Date date) {
        return toUTC(date,DATE_FORMAT);
    }
}