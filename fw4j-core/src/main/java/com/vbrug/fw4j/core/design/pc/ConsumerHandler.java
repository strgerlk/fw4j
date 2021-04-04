package com.vbrug.fw4j.core.design.pc;

/**
 * 消费者处理器
 *
 * @author vbrug
 * @since 1.0.0
 */
public abstract class ConsumerHandler <T> {

    public abstract void consume(T t) throws Exception;

    public void close(){}

}
