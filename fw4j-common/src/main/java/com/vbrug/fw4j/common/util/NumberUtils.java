package com.vbrug.fw4j.common.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vbrug
 * @since 1.0.0
 */
public abstract class NumberUtils {


    public static List<String> avgSplitNumber(String startValue, String endValue, int num) {
        long         startTime = Long.parseLong(startValue);
        long         endTime   = Long.parseLong(endValue);
        long         avgTime   = (endTime - startTime) / (long) num;
        List<String> dateList  = new ArrayList();
        dateList.add(startValue);
        for (int i = 1; i < num; ++i) {
            dateList.add(Long.toString(startTime + avgTime * (long) i));
        }
        dateList.add(endValue);
        return dateList;
    }

    @SuppressWarnings({"rawtypes", "unchecked", "WrapperTypeMayBePrimitive"})
    public static <V> List<V> avgSplitNumber(V start, V end, int num) {
        List<V> valueList = new ArrayList();
        valueList.add(start);
        if (start instanceof Long) {
            Long startLong = (Long) start;
            Long endLong   = (Long) end;
            Long avgLong   = (endLong - startLong) / num;
            for (int i = 1; i < num; ++i) {
                Long value = startLong + avgLong * i;
                valueList.add((V) value);
            }
        } else if (start instanceof Integer) {
            Integer startInteger = (Integer) start;
            Integer endInteger   = (Integer) end;
            Integer avgInteger   = (endInteger - startInteger) / num;
            for (int i = 1; i < num; ++i) {
                Integer value = startInteger + avgInteger * i;
                valueList.add((V) value);
            }
        }
        valueList.add(end);
        return valueList;
    }

    /**
     * 计算百分比
     * @param num1 除数
     * @param num2 被除数
     * @return 百分比数值
     */
    public static String divisionPercent(Number num1, Number num2) {
        Assert.isTrue(num2.doubleValue() != 0D, "被除数不可以为0");
        DecimalFormat dec = new DecimalFormat("0.00");
        return dec.format(num1.doubleValue() / num2.doubleValue() * 100) + "%";
    }
}
