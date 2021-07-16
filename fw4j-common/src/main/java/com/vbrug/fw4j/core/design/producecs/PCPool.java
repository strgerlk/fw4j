package com.vbrug.fw4j.core.design.producecs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 生产消费池
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

    /**
     * 设置队列最大值
     * @param dequeMaxSize 队列最大值
     * @return 池自身对象
     */
    public PCPool<V> setDequeMaxSize(int dequeMaxSize) {
        pContext.setDequeMaxSize(dequeMaxSize);
        return this;
    }

    /**
     * 加入生产任务
     * @param producerTasks 生产任务
     * @return 池自身对象
     */
    public PCPool<V> push(ProducerTask<V>... producerTasks) {
        for (int i = 0; i < producerTasks.length; i++) {
            FutureTask<Integer> futureTask = new FutureTask<>(new ProducerCallable<>(producerTasks[i], pContext));
            Thread              thread     = new Thread(futureTask);
            thread.setName("thread_" + id + "_producer_" + i);
            threadList.add(thread);
            futureList.add(futureTask);
        }
        pContext.setProducerNumber(producerTasks.length);
        return this;
    }

    /**
     * 加入消费任务
     * @param consumerTasks 消费任务
     * @return 池自身对象
     */
    public PCPool<V> push(ConsumerTask<V>... consumerTasks) {
        for (int i = 0; i < consumerTasks.length; i++) {
            FutureTask<Integer> futureTask = new FutureTask<>(new ConsumerCallable<>(consumerTasks[i], pContext));
            Thread              thread     = new Thread(futureTask);
            thread.setName("thread_" + id + "_consumer_" + i);
            threadList.add(thread);
            futureList.add(futureTask);
        }
        return this;
    }

    /**
     * 执行生产消费
     * @throws Exception 异常
     */
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
