package com.vbrug.fw4j.core.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通知锁
 *
 * @author vbrug
 * @since 1.0.0
 */
public class SignalLock implements Lock {

    private final ReentrantLock lock;
    private final Condition condition;

    public SignalLock(boolean fair){
        this.lock = new ReentrantLock(fair);
        this.condition = this.lock.newCondition();
    }

    @Override
    public void lock() {
        this.lock.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return this.lock.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return this.lock.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        this.lock.unlock();
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public void await() throws InterruptedException {
        this.condition.await();
    }

    public void signal(){
        this.condition.signal();
    }

    public void signalAll(){
        this.condition.signalAll();
    }

    public boolean isLocked(){
        return this.lock.isLocked();
    }

    public boolean isHeldByCurrentThread(){
        return this.lock.isHeldByCurrentThread();
    }

    public int getHoldCount(){
        return this.lock.getHoldCount();
    }

    public boolean hasWaiters(){
        return this.lock.hasWaiters(this.condition);
    }

    public int getWaitQueueLength(){
        return this.lock.getWaitQueueLength(this.condition);
    }

    public int getQueueLength(){
        return this.lock.getQueueLength();
    }

}
