package com.vbrug.fw4j.core.design.pc;

import com.vbrug.fw4j.core.thread.SignalLock;
import com.vbrug.fw4j.core.thread.ThreadState;

import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 生产者
 *
 * @author vbrug
 * @since 1.0.0
 */
public class Producer<T> implements Runnable {

    private final Deque<T> deque;
    private final SignalLock lock;
    private final ProducerHandler<T> handler;
    private volatile Boolean isRunning = true;
    private volatile Boolean noProduceStop = false;
    private final SignalLock exitLock;
    protected volatile ThreadState state;
    private Exception exception;
    private volatile Boolean isWaitConsume = false;

    public Producer(ProducerHandler<T> handler, Deque<T> deque, SignalLock lock) {
        this.handler = handler;
        this.deque = deque;
        this.lock = lock;
        this.exitLock = new SignalLock(true);
    }

    public Producer(ProducerHandler<T> handler, Deque<T> deque, SignalLock lock, boolean noProduceStop, boolean isWaitConsume) {
        this(handler, deque, lock);
        this.noProduceStop = noProduceStop;
        this.isWaitConsume = isWaitConsume;
    }

    @Override
    public void run() {
        this.state = ThreadState.RUNNING;
        while (this.isRunning) {
            T t = null;
            try {
                while (isWaitConsume && deque.size() > 1)
                    TimeUnit.MILLISECONDS.sleep(1500);
                t = handler.produce();
            } catch (Exception e) {
                handler.close();
                this.isRunning = false;
                this.state = ThreadState.EXCEPTION;
                this.exception = e;
                e.printStackTrace();
            }
            if (Objects.isNull(t)) {
                if (noProduceStop)
                    break;
                continue;
            }
            lock.lock();
            deque.add(t);
            try {
                if (lock.hasWaiters())
                    lock.signal();
            } finally {
                lock.unlock();
            }
        }
        handler.close();
        this.state = ThreadState.STOPPING;
        this.exitLock.lock();
        try {
            if (this.exitLock.hasWaiters())
                this.exitLock.signalAll();
        } finally {
            this.exitLock.unlock();
        }
        this.state = ThreadState.STOP;
    }

    public void stop() {
        this.isRunning = false;
        this.commStop();
    }

    public boolean isStop() {
        return this.state == ThreadState.STOP || this.state == ThreadState.EXCEPTION;
    }

    public void finish() {
        this.noProduceStop = true;
        this.commStop();
    }

    private void commStop() {
        if (this.state == ThreadState.RUNNING) {
            this.exitLock.lock();
            try {
                if (this.state == ThreadState.RUNNING) {
                    this.exitLock.await();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                this.exitLock.unlock();
            }
        }
    }

    public void start() {
        new Thread(this).start();
    }
}
