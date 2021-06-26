package com.vbrug.fw4j.core.design.producecs;

import com.vbrug.fw4j.common.util.ObjectUtils;

import java.util.Deque;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ConsumerCallable<V> implements Callable<Integer> {

    private final Consumer<V> consumer;

    private final PContext<V> pContext;

    protected ConsumerCallable(Consumer<V> consumer, PContext<V> pContext) {
        this.consumer = consumer;
        this.pContext = pContext;
    }

    @Override
    public Integer call() throws Exception {
        try {
            // 01-消费前预置处理
            this.beforeConsume();

            // 02-消费
            this.consume();

            // 03-消费后置处理
            this.afterConsume();
        } finally {
            // 04-消费最终处理
            this.consumer.finallyHandle();
        }
        return 1;
    }


    private void beforeConsume() throws Exception {
        try {
            if (!pContext.isException())
                consumer.beforeConsume();
        } catch (Exception e) {
            this.exceptionHandle(e);
        }
    }

    private void consume() throws Exception {
        Deque<?>      dataDeque = pContext.getDataDeque();
        ReentrantLock mainLock  = pContext.getMainLock();

        while (!pContext.isException()) {
            V data = null;
            // 判断队列是否为空，为空则释放锁进行等待
            mainLock.lock();
            try {
                if (dataDeque.isEmpty()) {
                    if (pContext.isProduceFinish())
                        break;
                    pContext.getNotEmpty().await();
                }
                if (!pContext.isException()) {
                    if (dataDeque.isEmpty() && pContext.isProduceFinish())
                        break;
                    data = pContext.getDataDeque().poll();
                    if (mainLock.hasWaiters(pContext.getNotFull()))
                        pContext.getNotFull().signal();
                } else {
                    System.out.println(Thread.currentThread().getName() + " 收到异常通知");
                }
            } catch (Exception e) {
                this.exceptionHandle(e);
            } finally {
                mainLock.unlock();
            }
            // 消费数据
            try {
                if (!pContext.isException() && !ObjectUtils.isNull(data))
                    consumer.consume(data);
            } catch (Exception e) {
                this.exceptionHandle(e);
            }
        }
    }

    private void afterConsume() throws Exception {
        try {
            if (!pContext.isException())
                consumer.afterConsume();
        } catch (Exception e) {
            this.exceptionHandle(e);
        }
    }

    private void exceptionHandle(Exception e) throws Exception {
        pContext.getMainLock().lock();
        try {
            if (pContext.isException())
                throw e;
            pContext.setException(true);
            consumer.exceptionHandle(e);

            if (pContext.getMainLock().hasWaiters(pContext.getNotFull())) {
                System.out.println(Thread.currentThread().getName() + "下发NOT_FULL异常通知");
                pContext.getNotFull().signalAll();
            }
            if (pContext.getMainLock().hasWaiters(pContext.getNotEmpty())) {
                System.out.println(Thread.currentThread().getName() + "下发NOT_EMPTY异常通知");
                pContext.getNotEmpty().signalAll();
            }
            throw e;
        } finally {
            pContext.getMainLock().unlock();
        }
    }
}
