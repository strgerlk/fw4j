package com.vbrug.fw4j.common.util;

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
     * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalStateException} 异常<br>
     *
     * @param expression 校验表达式
     * @param message 异常信息
     * @exception IllegalStateException 表达式为false抛出
     */
    public static void state(boolean expression, String message) {
        if (! expression )
            throw new IllegalStateException(message);
    }

    /**
     * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalStateException} 异常<br>
     *
     * @param expression 校验表达式
     * @param messageSupplier 提供异常信息
     * @exception IllegalStateException 表达式为false抛出
     */
    public static void state(boolean expression, Supplier<String> messageSupplier){
        if (! expression)
            throw new IllegalStateException(nullSafeGet(messageSupplier));
    }

    /**
     * 断言文本是否不为空，且不为空字符，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
     *
     * @param text 待校验文本
     * @exception IllegalArgumentException 文本为空时抛出
     */
    public static void hasText(String text){
        hasText(text,
                "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    /**
     * 断言文本是否不为空，且不为空字符，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
     *
     * @param text 待校验文本
     * @param message 异常信息
     * @exception IllegalArgumentException 文本为空时抛出
     */
    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言对象是否不为空，如果为空则抛出 {@code IllegalArgumentException} 异常<br>
     *
     * @param object 待校验对象
     * @exception IllegalArgumentException 对象为空时抛出
     */
    public static void notNull(Object object){
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * 断言对象是否不为空，如果为空则抛出 {@code IllegalArgumentException} 异常<br>
     *
     * @param object 待校验对象
     * @param message 异常信息
     * @exception IllegalArgumentException 对象为空时抛出
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言对象是否为空，如果不为空抛出 {@code IllegalArgumentException} 异常<br>
     *
     * @param object 待校验对象
     * @exception IllegalArgumentException 对象不为空时抛出
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * 断言对象是否为空，如果不为空抛出 {@code IllegalArgumentException} 异常<br>
     *
     * @param object 待校验对象
     * @param message 异常信息
     * @exception IllegalArgumentException 对象不为空时抛出
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
     *
     * @param expression 校验表达式
     * @exception IllegalStateException 表达式为false抛出
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }


    /**
     * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
     *
     * @param expression 校验表达式
     * @param message 异常信息
     * @exception IllegalStateException 表达式为false抛出
     */
    public static void isTrue(boolean expression, String message){
        if (! expression)
            throw new IllegalArgumentException(message);
    }

    private static String nullSafeGet(Supplier<String> messageSupplier){
        return Optional.ofNullable(messageSupplier).map(Supplier::get).orElse(null);
    }


}
