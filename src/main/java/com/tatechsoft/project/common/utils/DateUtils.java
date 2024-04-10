package com.tatechsoft.project.common.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMM = "yyyyMM";
    public static final String DD = "dd";
    public static final String MMM = "MMM";
    public static final String MM = "MM";
    public static final String MMMM = "MMMM";
    public static final String YYYY = "yyyy";
    public static final String MM_YYYY = "MM/yyyy";
    public static final String MMM_YYYY_SPAC = "MMM yyyy";
    public static final String DD_MM_YYYY = "dd/MM/yyyy";
    public static final String DD_MM_YYYY_DOT = "dd.MM.yyyy";
    public static final String DD_MMM_YYYY_SPAC = "dd MMM yyyy";
    public static final String DD_MMMM_YYYY_SPAC = "dd MMMM yyyy";
    public static final String DD_MM_YY = "dd/MM/yy";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDD_HH_MM_SS = "yyyyMMdd HH:mm:ss";
    public static final String DD_MM_YYYY_HHMMSS = "dd/MM/yyyy HH:mm:ss";
    public static final String DD_MMM_YYYY_HHMMSS = "dd/MMM/yyyy HH:mm:ss";
    public static final String DD_MMM_YYYY_HHMM = "dd/MMM/yyyy HH:mm";

    public static final Locale LOCAL_TH = new Locale("th", "TH");
    public static final Locale LOCAL_EN = new Locale("en", "US");

    public static final String FULL_MONTH = "FULL_MONTH";
    public static final String SHORT_MONTH = "SHORT_MONTH";

    public static String formatDate(Date date, String patten, Locale locale) {
        String dateString = "";
        try {
            if (date != null) {
                dateString = DateFormatUtils.format(date, patten, locale);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return dateString;
    }

    public static String formatDate(Date date, String patten) {
        String dateString = "";
        try {
            if (date != null) {
                dateString = DateFormatUtils.format(date, patten, LOCAL_EN);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return dateString;
    }

    public static Date parseDate(String strDate, String patten, Locale locale) {
        Date dateString = null;
        try {
            if (StringUtils.isNotBlank(strDate)) {
                dateString = org.apache.commons.lang3.time.DateUtils.parseDate(strDate, locale, patten);
            }
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }
        return dateString;
    }

    public static Date parseDate(String strDate, String patten) {
        Date dateString = null;
        try {
            if (StringUtils.isNotBlank(strDate)) {
                dateString = org.apache.commons.lang3.time.DateUtils.parseDate(strDate, LOCAL_EN, patten);
            }
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }
        return dateString;
    }

    public static String lastOfMonth(Date date) {
        String day = "";
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);

            Date lastDayOfMonth = calendar.getTime();
            day = DateUtils.formatDate(lastDayOfMonth, DD);
        }
        return day;
    }

    public static Date setDateFrom(Date date) {
        if (ObjectUtils.isNotEmpty(date)) return null;
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }

    public static Date setDateTo(Date date) {
        if (ObjectUtils.isEmpty(date)) return null;
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date;
    }

    public static LocalDate convertToLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
