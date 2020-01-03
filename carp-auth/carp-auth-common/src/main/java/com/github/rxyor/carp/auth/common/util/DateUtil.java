package com.github.rxyor.carp.auth.common.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 *<p>
 *日期工具类
 *</p>
 *
 * @author liuyang
 * @date 2018-12-07 Fri 10:18:30
 * @since 1.0.0
 */
public class DateUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static long getTimestamp() {
        return getTimestamp(new Date());
    }

    public static Date parse(String str) throws ParseException {
        if (StringUtils.isEmpty(str)) {
            throw new ParseException("日期字符串不能为空", -1);
        }
        return DateUtils.parseDate(str, Format.commonForamtInArray());
    }

    public static Date parse(String str,final Locale locale) throws ParseException {
        if (StringUtils.isEmpty(str)) {
            throw new ParseException("日期字符串不能为空", -1);
        }
        return DateUtils.parseDate(str,locale, Format.commonForamtInArray());
    }

    public static Date parse(String str, String pattern) throws ParseException {
        if (StringUtils.isEmpty(str)) {
            throw new ParseException("日期字符串不能为空", -1);
        }
        if (StringUtils.isEmpty(pattern)) {
            throw new ParseException("日期格式不能为空", -1);
        }
        return DateUtils.parseDate(str, pattern);
    }

    public static Date parse(String str, final Locale locale, String pattern) throws ParseException {
        if (StringUtils.isEmpty(str)) {
            throw new ParseException("日期字符串不能为空", -1);
        }
        if (StringUtils.isEmpty(pattern)) {
            throw new ParseException("日期格式不能为空", -1);
        }
        return DateUtils.parseDate(str, locale, pattern);
    }

    /**
     * 获取时间戳
     *
     * @param date 日期
     * @return linux时间戳
     */
    public static long getTimestamp(Date date) {
        Objects.requireNonNull(date, "date不能是null");
        return date.getTime();
    }

    public static long getSecondTime(int second) {
        return 1000 * second;
    }

    public static long getMinuteTime(int minute) {
        return getSecondTime(60) * minute;
    }

    public static long getHourTime(int hour) {
        return getMinuteTime(60) * hour;
    }

    public static long getDayTime(int day) {
        return getHourTime(24) * day;
    }

    /**
     * 日期格式
     */
    @SuppressWarnings("all")
    public enum Format {
        DEFAULT("yyyy-MM-dd HH:mm:ss"),
        F1("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
        F2("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
        F3("yy-MM-dd HH:mm:ss am"),
        F4("EEE MMM dd HH:mm:ss 'GMT' yyyy"),
        F5("yyyy/MM/dd HH:mm:ss"),
        F6("yyyy/MM/dd'T'HH:mm:ss.SSSZ"),
        F7("yyyy/MM/dd'T'HH:mm:ss.SSSXXX"),
        F8("yy/MM/dd HH:mm:ss am"),
        F9("yy-MM-dd HH:mm"),
        F10("yyyy.MM.dd G 'at' HH:mm:ss z"),
        F11("yyyyy.MMMMM.dd GGG hh:mm aaa"),
        F12("EEE, d MMM yyyy HH:mm:ss Z"),
        F13("yyMMddHHmmssZ"),
        F14("EEE, MMM d, ''yy"),
        F15("YYYY-'W'ww-u"),
        F16("yy-MM-dd"),
        F17("yy/MM/dd"),
        F18("HH:mm:ss"),
        F19("hh 'o''clock' a, zzzz"),
        F20("K:mm a, z"),
        F21("h:mm a"),
        F22("yy/MM/dd 上午HH:mm:ss"),
        F23("yy/MM/dd 下午HH:mm:ss"),
        F24("yyyy年MM月dd日 HH时mm分ss秒"),
        F25("yyyy年MM月dd日 HH时mm分ss秒 E"),
        F26("EEE, dd MMM yyyy HH:mm:ss 'GMT'"),
        F27("yy/MM/dd HH:mm");


        private String value;

        Format(String format) {
            this.value = format;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static String[] commonForamtInArray() {
            List<String> list = commonFormatInList();
            return list.toArray(new String[list.size()]);
        }

        public static List<String> commonFormatInList() {
            List<String> list = new ArrayList<>(32);
            for (Format format : Format.values()) {
                list.add(format.getValue());
            }
            return list;
        }
    }
}
