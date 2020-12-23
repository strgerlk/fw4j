package com.vbrug.fw4j.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
     * 获取默认日期
     *
     * @return 返回当前日期，格式为【yyyy-MM-dd】
     */
    public static String getCurrentDate(){
        return formatDate(new Date(), DateUtils.YYYY_MM_DD);
    }

    /**
     * 获取默认时间
     *
     * @return 返回当前时间，格式为【yyyy-MM-dd HH:mm:ss】
     */
    public static String getCurrentDateTime(){
        return formatDate(new Date(), DateUtils.YMDHMS);
    }

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
     * 时间格式化
     *
     * @param date 待格式化日期
     * @param pattern 日期格式
     * @return 格式化后的字符串
     */
    public static String formatTime(long time, String pattern){
        Assert.notNull(time, "Date must be not be null");
        Assert.notNull(pattern, "Pattern must be not be null");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(time);
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
        Assert.notNull(date, "The date must not be null");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 比较时间
     */
    public static int compare(String xDate, String yDate, String pattern){
        Assert.notNull(xDate, "The firstDate must not be null");
        Assert.notNull(xDate, "The endDate must not be null");
        Date fDate = DateUtils.parseDate(xDate, YMDHMS);
        Date sDate = DateUtils.parseDate( yDate, YMDHMS);
        return Long.compare(fDate.getTime(), sDate.getTime());
    }


    /**
     * 日期均分
     */
    public static List<String> avgSplitDate(String startDate, String endDate, int num){
        long startTime = DateUtils.parseDate(startDate, DateUtils.YMDHMS).getTime();
        long  endTime =  DateUtils.parseDate(endDate, DateUtils.YMDHMS).getTime();
        long avgTime = (endTime - startTime) / num;
        List<String>  dateList = new ArrayList<>();
        dateList.add(startDate);
        for (int i = 1; i < num; i++) {
            dateList.add(DateUtils.formatTime(startTime+avgTime*i, DateUtils.YMDHMS));
        }
        dateList.add(endDate);
        return dateList;
    }

    public static void main(String[] args) {
        System.out.println(avgSplitDate("2020-01-01 05:29:29", "2020-08-01 05:29:29", 4));
    }

}
