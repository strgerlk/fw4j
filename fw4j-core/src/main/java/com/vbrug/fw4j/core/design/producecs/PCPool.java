package com.vbrug.fw4j.core.design.producecs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class PCPool<V> {
    private       String                id;
    private final PContext<V>           pContext   = new PContext<>();
    private final List<Future<Integer>> futureList = new ArrayList<>();
    private final List<Thread>          threadList = new ArrayList<>();

    public PCPool() {}

    public PCPool(String id) {
        this.id = id;
    }

    public PCPool<V> setDequeMaxSize(int dequeMaxSize) {
        pContext.setDequeMaxSize(dequeMaxSize);
        return this;
    }

    public PCPool<V> push(Producer<V>... producers) {
        for (int i = 0; i < producers.length; i++) {
            FutureTask<Integer> futureTask = new FutureTask<>(new ProducerCallable<>(producers[i], pContext));
            Thread              thread     = new Thread(futureTask);
            thread.setName("thread_" + id + "_producer_" + i);
            threadList.add(thread);
            futureList.add(futureTask);
        }
        pContext.setProducerNumber(producers.length);
        return this;
    }

    public PCPool<V> push(Consumer<V>... consumers) {
        for (int i = 0; i < consumers.length; i++) {
            FutureTask<Integer> futureTask = new FutureTask<>(new ConsumerCallable<>(consumers[i], pContext));
            Thread              thread     = new Thread(futureTask);
            thread.setName("thread_" + id + "_consumer_" + i);
            threadList.add(thread);
            futureList.add(futureTask);
        }
        return this;
    }

    public void run() throws Exception {
        // 启动线程
        for (Thread thread : threadList) {
            thread.start();
        }
        // 等待任务执行结束
        Exception exception = null;
        for (Future<Integer> future : futureList) {
            try {
                future.get();
            } catch (Exception e) {
                exception = e;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }
}
