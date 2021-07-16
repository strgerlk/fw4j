package com.vbrug.fw4j.core.design.producecs;

import com.vbrug.fw4j.common.exception.Fw4jException;

/**
 * 消费任务
 * @author vbrug
 * @since 1.0.0
 */
public interface ConsumerTask<T> {


    /**
     * 消费
     * @param t 消费对象
     * @throws Exception 异常信息
     */
    void consume(T t) throws Exception;

    /**
     * 任务切割
     * @param number 切割任务数量
     * @return 任务集合
     * @throws Exception 异常
     */
    default ConsumerTask<T>[] split(int number) throws Exception {
        throw new Fw4jException("消费者未实现该方法，不可进行任务切割");
    }

    /**
     * 消费任务消费开始前置处理
     * @throws Exception 异常信息
     */
    default void beforeConsume() throws Exception {}

    /**
     * 消费任务消费完成后置处理
     * @throws Exception 异常信息
     */
    default void afterConsume() throws Exception {}

    /**
     * 异常处理
     * @param e 异常信息
     * @throws Exception 异常
     */
    default void exceptionHandle(Exception e) throws Exception {}

    /**
     * 无论是否发生异常都需执行的方法
     * @throws Exception 异常
     */
    default void finallyHandle() throws Exception {}
}
