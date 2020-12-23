package com.vbrug.fw4j.core.design.pc;

import com.vbrug.fw4j.core.thread.SignalLock;
import com.vbrug.fw4j.core.thread.ThreadState;

import java.util.Deque;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class Consumer<T> implements Runnable {

    private final ConsumerHandler<T> handler;
    private final Deque<T> deque;
    private final SignalLock lock;
    private volatile Boolean isRunning = true;
    private final SignalLock exitLock;
    protected volatile ThreadState state;
    private volatile Boolean dequeNullStop = false;

    public Consumer(ConsumerHandler<T> handler, Deque<T> deque, SignalLock lock) {
        this.lock = lock;
        this.deque = deque;
        this.handler = handler;
        this.exitLock = new SignalLock(true);
    }

    @Override
    public void run() {
        this.state = ThreadState.LOCKING;
        while (isRunning) {
            if (deque.isEmpty()) {
                if (dequeNullStop)
                    break;
                lock.lock();
                if (deque.isEmpty()) {
                    try {
                        lock.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        lock.unlock();
                    }
                } else {
                    lock.unlock();
                }
                this.state = ThreadState.EXECUTING;
            }
            if (!deque.isEmpty()) {
                T poll = deque.poll();
                if (poll != null && isRunning) {
                    try {
                        handler.consume(poll);
                    } catch (Exception e) {
                        this.isRunning = false;
                        e.printStackTrace();
                    }
                }
            }
            this.state = ThreadState.LOCKING;
        }

        this.state = ThreadState.STOPPING;
        this.exitLock.lock();
        try {
            if (this.exitLock.hasWaiters())
                this.exitLock.signal();
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
        return this.state == ThreadState.STOP;
    }

    public void finish() {
        this.dequeNullStop = true;
        this.commStop();
    }

    private void commStop() {
        // 解除任务等待
        while (this.state == ThreadState.LOCKING) {
            lock.lock();
            try {
                if (lock.hasWaiters()) {
                    lock.signal();
                }
            } finally {
                lock.unlock();
            }
        }

        // 等待任务结束
        if (this.state == ThreadState.EXECUTING) {
            this.exitLock.lock();
            try {
                if (this.state == ThreadState.LOCKING || this.state == ThreadState.EXECUTING) {
                    this.exitLock.await();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                this.exitLock.unlock();
            }
        }
    }

    public void start(){
        new Thread(this).start();
    }
}
