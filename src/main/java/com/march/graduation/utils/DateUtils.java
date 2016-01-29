
package com.march.graduation.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public final class DateUtils {

    public static String convertDateToStr(DateConvertType dateConvertType, Date date) {
        return dateConvertType.convertDateToStr(date);
    }

    public static DateTime convertStrToDateTime(DateConvertType dateConvertType, String dateStr) {
        return dateConvertType.convertStrToDateTime(dateStr);
    }

    public enum DateConvertType {

        YYYY_MM_DD_FORMAT() {
            @Override
            public String convertDateToStr(Date date) {
                if (date == null) {
                    return null;
                }
                return dateFormatByNormal.format(date);
            }
        },

        YYYYMMDD_FORMAT() {
            @Override
            public String convertDateToStr(Date date) {
                if (date == null) {
                    return null;
                }
                return dateFormatByNonSplit.format(date);
            }
        },

        YYYY_MM_DD_HH_MM_FORMAT() {
            @Override
            public String convertDateToStr(Date date) {
                if (date == null) {
                    return null;
                }
                return dateFormatByAll.format(date);
            }
        },

        YYYY_MM_DD_DATETIME() {
            @Override
            public DateTime convertStrToDateTime(String dateStr) {
                if (StringUtils.isBlank(dateStr)) {
                    return null;
                }
                return dateParseByNormal.parseDateTime(dateStr);
            }
        },

        YYYY_MM_DD_HH_MM_DATETIME() {
            @Override
            public DateTime convertStrToDateTime(String dateStr) {
                if (StringUtils.isBlank(dateStr)) {
                    return null;
                }
                return dateParseByAll.parseDateTime(dateStr);
            }
        },

        YYYYMMDDHHMM_DATETIME() {
            @Override
            public DateTime convertStrToDateTime(String dateStr) {
                if (StringUtils.isBlank(dateStr)) {
                    return null;
                }
                return dateParseByAllNonSplit.parseDateTime(dateStr);
            }
        },

        HH_MM_DATETIME() {
            @Override
            public DateTime convertStrToDateTime(String dateStr) {
                if (StringUtils.isBlank(dateStr)) {
                    return null;
                }
                return fmt.parseDateTime(dateStr);
            }
        };

        public String convertDateToStr(Date date) {
            throw new UnsupportedOperationException();
        }

        public DateTime convertStrToDateTime(String dateStr) {
            throw new UnsupportedOperationException();
        }

        private static final FastDateFormat dateFormatByNormal = FastDateFormat.getInstance("yyyy-MM-dd");

        private static final FastDateFormat dateFormatByNonSplit = FastDateFormat.getInstance("yyyyMMdd");

        private static final FastDateFormat dateFormatByAll = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");

        private static final DateTimeFormatter dateParseByNormal = DateTimeFormat.forPattern("yyyy-MM-dd");

        private static final DateTimeFormatter dateParseByAll = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        private static final DateTimeFormatter dateParseByAllNonSplit = DateTimeFormat.forPattern("yyyyMMddHHmm");

        private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");

    }
}
