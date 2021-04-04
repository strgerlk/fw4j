package com.vbrug.fw4j.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vbrug
 * @since 1.0.0
 */
public abstract class NumberUtils {


    public static List<String> avgSplitNumber(String startValue, String endValue, int num) {
        long startTime = Long.parseLong(startValue);
        long endTime = Long.parseLong(endValue);
        long avgTime = (endTime - startTime) / (long) num;
        List<String> dateList = new ArrayList();
        dateList.add(startValue);
        for (int i = 1; i < num; ++i) {
            dateList.add(Long.toString(startTime + avgTime * (long) i));
        }
        dateList.add(endValue);
        return dateList;
    }

}
