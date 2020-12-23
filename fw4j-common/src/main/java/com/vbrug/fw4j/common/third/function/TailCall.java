package com.vbrug.fw4j.common.third.function;

import java.util.stream.Stream;

@FunctionalInterface
public interface TailCall<T> {

    TailCall<T> apply();

    default boolean isComplete() {
        return false;
    }

    default T result() {
        throw new Error("not implemented");
    }

    default T invoke() {
        return Stream.iterate(this, x -> x.apply())
                .filter(x -> x.isComplete()).findFirst().get().result();
    }

}
