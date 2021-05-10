package com.vbrug.fw4j.common;

import java.util.*;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class Test {

    public static void main(String[] args) {
        List<Map<String, String>> list1 = new ArrayList<>();
        List<Map<String, String>> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("辖区"+i, String.valueOf(i));
            list1.add(map);
            Map<String, String> map1 = new HashMap<>();
            map1.put("辖区"+i, String.valueOf(i*5));
            list2.add(map1);
        }

        list1.stream().forEach(x -> {
            String key = x.keySet().iterator().next();
            long   list1Num   = Long.parseLong(x.get(key));
            Map<String, String> list2Map = list2.stream().filter(y -> y.keySet().contains(key)).findFirst().get();
            long list2Num = Long.parseLong(list2Map.get(key));
            x.put(key, String.valueOf(list1Num + list2Num));
        });
        list1.stream().sorted((x, y)->{
            String key = x.keySet().iterator().next();
            long   xNum   = Long.parseLong(x.get(key));
            String key1 = y.keySet().iterator().next();
            long   yNum   = Long.parseLong(y.get(key));
            return Long.compare(xNum, yNum);
        });

    }
}
