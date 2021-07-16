package com.vbrug.fw4j.core.design.producecs;

import com.vbrug.fw4j.common.exception.Fw4jException;

/**
 * 生产任务
 * @author vbrug
 * @since 1.0.0
 */
public interface ProducerTask<T> {

    /**
     * 生产
     * @return 生产结果
     * @throws Exception 异常信息
     */
    T produce() throws Exception;

    /**
     * 任务切割
     * @param number 切割任务数量
     * @return 任务集合
     * @throws Exception 异常
     */
    default ProducerTask<T>[] split(int number) throws Exception {
        throw new Fw4jException("生产者未实现该方法，不可进行任务切割");
    }

    /**
     * 生产任务生产开始时前置处理
     * @throws Exception 异常信息
     */
    default void beforeProduce() throws Exception {}

    /**
     * 生产任务生产完成后后置处理
     * @throws Exception 异常信息
     */
    default void afterProduce() throws Exception {}

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
