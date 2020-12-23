package com.vbrug.fw4j.common.algorithms;

/**
 * 雪花算法
 *
 * 分布式ID生成工具
 */
public class SnowFlake {

    private final static long SEQUENCE_BIT = 12;
    private final static long MACHINE_BIT = 5;
    private final static long DATA_CENTER_BIT = 5;

    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private final long dataCenterId;
    private final long machineId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowFlake(long dataCenterId, long machineId){
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0){
            throw new IllegalArgumentException(
                    "dataCenterId can't be greater than "
                            + MAX_DATA_CENTER_NUM + " or less than 0");
        }

        if (machineId > MAX_MACHINE_NUM || machineId < 0){
            throw new IllegalArgumentException("machineId can't be greater than "
                    + MAX_MACHINE_NUM + " or less than 0 ");
        }

        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 生成下一ID
     *
     * @return id
     */
    public long nextId(){
        long currTimestamp;
        // 加锁保证ID唯一
        synchronized (this) {
            currTimestamp = System.currentTimeMillis();
            if (currTimestamp < lastTimestamp) {
                throw new RuntimeException("Clock moved backwards. refusing to generate id!");
            }

            // 若与上一ID处于统一毫秒，增加sequence
            if (currTimestamp == lastTimestamp) {
                sequence = (sequence + 1) & MAX_SEQUENCE;
                if (sequence == 0L) currTimestamp = getNextMill();
            } else {
                sequence = 0L;
            }
            lastTimestamp = currTimestamp;
        }
        return currTimestamp << TIMESTAMP_LEFT
                | dataCenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill(){
        long mill = System.currentTimeMillis();
        while (mill <= lastTimestamp)
            mill = System.currentTimeMillis();
        return mill;
    }

}
