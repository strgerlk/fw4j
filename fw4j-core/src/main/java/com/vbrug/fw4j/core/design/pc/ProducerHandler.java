package com.vbrug.fw4j.core.design.pc;

/**
 * @author vbrug
 * @since 1.0.0
 */
public abstract class ProducerHandler<T> {

    public abstract T produce() throws Exception;

    public void close() {
    }

}
