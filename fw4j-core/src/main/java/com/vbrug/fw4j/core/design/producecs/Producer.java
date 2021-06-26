package com.vbrug.fw4j.core.design.producecs;

/**
 * @author vbrug
 * @since 1.0.0
 */
public interface Producer<T> {

    T produce() throws Exception;

    default Producer<T>[] split(int number) throws Exception {
        return null;
    }

    default void beforeProduce() throws Exception {}

    default void afterProduce() throws Exception {}

    default void exceptionHandle(Exception e) throws Exception {}

    default void finallyHandle() throws Exception {}
}
