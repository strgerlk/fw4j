package com.vbrug.fw4j.core.annotation;

import java.lang.annotation.*;

/**
 * @author LK
 * @since 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeConsume {

    public enum TimeLevel {MICROSECOND, MILLISECOND}

    TimeLevel value() default TimeLevel.MILLISECOND;
}
