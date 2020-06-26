package com.vbrug.fw4j.common.util;

import com.sun.istack.internal.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 断言
 *
 * @author vbrug
 * @since 1.0.0
 */
public abstract class Assert {

    /**
     *
     */
    public static void state(boolean expression, String message) {
        if (! expression )
            throw new IllegalStateException(message);
    }

    public static void state(boolean expression, Supplier<String> messageSupplier){
        if (! expression)
            throw new IllegalStateException(nullSafeGet(messageSupplier));
    }


    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code>
     * if the test result is <code>false</code>.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if expression is <code>false</code>
     */
    public static void isTrue(boolean expression, String message){
        if (! expression)
            throw new IllegalArgumentException(message);
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier){
        return Optional.ofNullable(messageSupplier).map(Supplier::get).orElse(null);
    }
}
