package com.vbrug.fw4j.core.design.producecs;

/**
 * @author vbrug
 * @since 1.0.0
 */
public interface Consumer<T> {


    void consume(T t) throws Exception;

    default Consumer<T>[] split(int number) throws Exception {
        return null;
    }

    default void beforeConsume() throws Exception {}

    default void afterConsume() throws Exception {}

    default void exceptionHandle(Exception e) throws Exception {}

    default void finallyHandle() throws Exception {}
}
