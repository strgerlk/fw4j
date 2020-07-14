package com.vbrug.fw4j.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author vbrug
 * @since 1.0.0
 */
public abstract class DateUtils {

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期解析
     *
     * @param dateStr 待解析字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date parseDate(String dateStr, String pattern){
        Assert.notNull(dateStr, "Date must be not be null");
        Assert.notNull(pattern, "Pattern must be not be null");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return  sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalStateException("Date parse exception");
        }
    }

    /**
     * 时间格式化
     *
     * @param date 待格式化日期
     * @param pattern 日期格式
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date, String pattern){
        Assert.notNull(date, "Date must be not be null");
        Assert.notNull(pattern, "Pattern must be not be null");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 日期计算，增加年
     *
     * @param date  日期
     * @param amount  增加数量，可以为负数
     * @return 计算后的新日期对象
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 日期计算，增加月
     *
     * @param date  日期
     * @param amount  增加数量，可以为负数
     * @return 计算后的新日期对象
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 日期计算，增加周
     *
     * @param date  日期
     * @param amount  增加数量，可以为负数
     * @return 计算后的新日期对象
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    /**
     * 日期计算，增加日
     *
     * @param date  日期
     * @param amount  增加数量，可以为负数
     * @return 计算后的新日期对象
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 日期计算，不修改原始日期对象，返回新日期对象
     *
     * @param date  日期
     * @param calendarField 日期字段
     * @param amount  增加数量，可以为负数
     * @return 计算后的新日期对象
     * @throws IllegalArgumentException if the date is null
     */
    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

}
