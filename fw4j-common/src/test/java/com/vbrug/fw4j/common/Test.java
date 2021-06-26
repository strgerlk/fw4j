package com.vbrug.fw4j.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class Test {
    private static final Map<Long, Long> synchronizedLockMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Long aLong1 = Long.valueOf(System.currentTimeMillis());
        Long aLong2 = Long.valueOf(System.currentTimeMillis());
        System.out.println(aLong1 == aLong2);
        new Thread() {
            @Override
            public void run() {
                synchronized (Test.getLock(aLong1)) {
                    int i = 0;
                    while (true) {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "_______" + System.currentTimeMillis());
                        i++;
                        if (i > 20)
                            break;
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                synchronized (Test.getLock(aLong2)) {
                    int i = 0;
                    while (true) {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "_______" + System.currentTimeMillis());
                        i++;
                        if (i > 20)
                            break;
                    }
                }
            }
        }.start();

    }


    private static Long getLock(Long id) {
        if (synchronizedLockMap.containsKey(id))
            return synchronizedLockMap.get(id);
        synchronizedLockMap.put(id, id);
        return id;
    }

    public static void main2(String[] args) {
        List<Map<String, String>> list1 = new ArrayList<>();
        List<Map<String, String>> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("辖区" + i, String.valueOf(i));
            list1.add(map);
            Map<String, String> map1 = new HashMap<>();
            map1.put("辖区" + i, String.valueOf(i * 5));
            list2.add(map1);
        }

        list1.stream().forEach(x -> {
            String              key      = x.keySet().iterator().next();
            long                list1Num = Long.parseLong(x.get(key));
            Map<String, String> list2Map = list2.stream().filter(y -> y.keySet().contains(key)).findFirst().get();
            long                list2Num = Long.parseLong(list2Map.get(key));
            x.put(key, String.valueOf(list1Num + list2Num));
        });
        list1.stream().sorted((x, y) -> {
            String key  = x.keySet().iterator().next();
            long   xNum = Long.parseLong(x.get(key));
            String key1 = y.keySet().iterator().next();
            long   yNum = Long.parseLong(y.get(key));
            return Long.compare(xNum, yNum);
        });

    }
}
