package com.vbrug.fw4j.core.design.producecs;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class PContext<V> {

    private final    Deque<V>      dataDeque       = new ConcurrentLinkedDeque<>();
    private final    ReentrantLock mainLock        = new ReentrantLock();
    private final    Condition     notFull         = mainLock.newCondition();
    private final    Condition     notEmpty        = mainLock.newCondition();
    private volatile Boolean       isException     = false;
    private volatile Boolean       isProduceFinish = false;
    private volatile int           dequeMaxSize    = 0;
    private          AtomicInteger producerNumber;

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
