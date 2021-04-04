package com.vbrug.fw4j.core.function;

/**
 * @author vbrug
 * @since 1.0.0
 */
@FunctionalInterface
public interface Supplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Exception;


}
