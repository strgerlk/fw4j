package com.vbrug.fw4j.common.util;


import com.vbrug.fw4j.common.algorithms.SnowFlake;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * ID 生成工具
 */
public abstract class IdGenerator {

    private static volatile long lastTimestamp = 0L;

    private static final SnowFlake SNOW_FLAKE = new SnowFlake(1, 1);

    /**
     * Retrieve a UUID
     * @return A randomly generated UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 移除UUID的横线
     * @return
     */
    public static String randomUUIDRmLine() {
        return randomUUID().replaceAll("-", "");
    }

    /**
     * 获取毫秒级日期字符串
     * <p>保证唯一加锁，注意高并发效率</p>
     * @return 返回日期毫秒级字符串
     */
    public static String nextDateTime() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(IdGenerator.nextTimestamp()));
    }

    /**
     * 获取毫秒级日期数字
     * <p>保证唯一加锁，注意高并发效率</p>
     * @return 返回日期毫秒级数字
     */
    public static long nextDateTime2() {
        return Long.parseLong(nextDateTime());
    }

    /**
     * 雪花算法生成ID
     * <pre>每毫秒可生成4096个ID</pre>
     * @return ID
     */
    public static long nextId() {
        return SNOW_FLAKE.nextId();
    }

    /**
     * 线程安全获取时间戳
     * @return timestamp
     */
    public static synchronized Long nextTimestamp() {
        long currTimestamp;
        synchronized (IdGenerator.class) {
            currTimestamp = System.currentTimeMillis();
            while (lastTimestamp == currTimestamp) {
                currTimestamp = System.currentTimeMillis();
            }
            lastTimestamp = currTimestamp;
        }
        return currTimestamp;
    }

}
