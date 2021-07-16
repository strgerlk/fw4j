package com.vbrug.fw4j.core.design.producecs;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产消费环境对象
 * @author vbrug
 * @since 1.0.0
 */
public class PContext<V> {

    private final    Deque<V>      dataDeque       = new ConcurrentLinkedDeque<>();         // 数据队列
    private final    ReentrantLock mainLock        = new ReentrantLock();                   // 线程锁
    private final    Condition     notFull         = mainLock.newCondition();               // 队列满时，生产等待
    private final    Condition     notEmpty        = mainLock.newCondition();               // 队列空时，消费等待
    private volatile Boolean       isException     = false;                                 // 异常状态
    private volatile Boolean       isProduceFinish = false;                                 // 生产状态
    private volatile int           dequeMaxSize    = 0;                                     // 队列最大数量限制
    private          AtomicInteger producerNumber;                                          // 生产者数量，用于判断生产是否已完成

    protected PContext() {}

    public void setDequeMaxSize(int dequeMaxSize) {
        this.dequeMaxSize = dequeMaxSize;
    }

    public void setProducerNumber(Integer producerNumber) {
        this.producerNumber = new AtomicInteger(producerNumber);
    }

    public AtomicInteger getProducerNumber() {
        return producerNumber;
    }

    public int getDequeMaxSize() {
        return dequeMaxSize;
    }

    public Deque<V> getDataDeque() {
        return dataDeque;
    }

    public ReentrantLock getMainLock() {
        return mainLock;
    }

    public Condition getNotFull() {
        return notFull;
    }

    public Condition getNotEmpty() {
        return notEmpty;
    }

    public Boolean isException() {
        return isException;
    }

    public void setException(Boolean running) {
        isException = running;
    }

    public Boolean isProduceFinish() {
        return isProduceFinish;
    }

    public void setProduceFinish(Boolean produceFinish) {
        isProduceFinish = produceFinish;
    }
}
