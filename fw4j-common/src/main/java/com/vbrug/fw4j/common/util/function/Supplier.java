package com.vbrug.fw4j.common.util.function;

@FunctionalInterface
public interface Supplier <T>{

    T get() throws Exception;
}
