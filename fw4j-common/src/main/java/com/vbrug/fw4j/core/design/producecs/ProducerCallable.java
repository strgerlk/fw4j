package com.vbrug.fw4j.core.design.producecs;

import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产线程
 * @author vbrug
 * @since 1.0.0
 */
public class ProducerCallable<V> implements Callable<Integer> {

    private final ProducerTask<V> producerTask;

    private final PContext<V> pContext;

    protected ProducerCallable(ProducerTask<V> producerTask, PContext<V> pContext) {
        this.producerTask = producerTask;
        this.pContext = pContext;
    }

    @Override
    public Integer call() throws Exception {
        try {
            // 01-生产前置处理
            this.beforeProduce();

            // 02-生产
            this.produce();

            // 03-生产后置处理
            this.afterProduce();
        } finally {
            // 04-生产终处理
            producerTask.finallyHandle();
        }
        return 1;
    }

    /**
     * 生产任务生产开始时前置处理
     * @throws Exception 异常信息
     */
    private void beforeProduce() throws Exception {
        try {
            if (!pContext.isException())
                producerTask.beforeProduce();
        } catch (Exception e) {
            this.exceptionHandle(e);
        }
    }

    /**
     * 进行生产
     * @throws Exception 生产异常
     */
    private void produce() throws Exception {
        Deque<?>      dataDeque = pContext.getDataDeque();
        ReentrantLock mainLock  = pContext.getMainLock();
        while (!pContext.isException()) {
            // 02-生产数据
            V data = null;
            try {
                data = producerTask.produce();
            } catch (Exception e) {
                this.exceptionHandle(e);
            }
            // 判断队列是否已满，已满则释放锁进行等待
            mainLock.lock();
            try {
                if (!pContext.isException()) {
                    // 判断生产是否已经完成
                    if (Objects.isNull(data)) {
                        if (pContext.getProducerNumber().decrementAndGet() == 0) {
                            pContext.setProduceFinish(true);
                            if (mainLock.hasWaiters(pContext.getNotEmpty()))
                                pContext.getNotEmpty().signalAll();
                        }
                        break;
                    }

                    if (pContext.getDequeMaxSize() != 0 && dataDeque.size() >= pContext.getDequeMaxSize())
                        pContext.getNotFull().await();
                    if (!pContext.isException()) {
                        pContext.getDataDeque().add(data);
                        if (mainLock.hasWaiters(pContext.getNotEmpty()))
                            pContext.getNotEmpty().signal();
                    } else {
                        System.out.println(Thread.currentThread().getName() + "收到异常通知二");
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + "收到异常通知一");
                }
            } catch (Exception e) {
                this.exceptionHandle(e);
            } finally {
                mainLock.unlock();
            }
        }
    }

    /**
     * 生产任务生产完成后置处理
     * @throws Exception 异常信息
     */
    private void afterProduce() throws Exception {
        try {
            if (!pContext.isException())
                producerTask.afterProduce();
        } catch (Exception e) {
            this.exceptionHandle(e);
        }
    }

    /**
     * 异常处理
     * @param e 异常信息
     * @throws Exception 异常
     */
    private void exceptionHandle(Exception e) throws Exception {
        pContext.getMainLock().lock();
        try {
            if (pContext.isException())
                throw e;
            pContext.setException(true);
            producerTask.exceptionHandle(e);
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
