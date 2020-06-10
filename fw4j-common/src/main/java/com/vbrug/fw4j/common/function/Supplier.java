package com.vbrug.fw4j.common.function;

@FunctionalInterface
public interface Supplier <T>{

    T get() throws Exception;
}
