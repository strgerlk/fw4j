package com.vbrug.fw4j.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 对象工具类
 *
 * @author vbrug
 * @since 1.0.0
 */
public abstract class ObjectUtils {

    /**
     * 判断对象为空，集合校验size
     */
    public static boolean isNull(Object... objs){
        return ObjectUtils.isEmpty(objs);
    }

    /**
     * 判断对象非空，集合校验size
     */
    public static boolean notNull(Object... objs) {
        return ObjectUtils.notEmpty(objs);
    }

    /**
     * 对象非空判断
     */
    public static boolean notEmpty(Object obj){
        return ! ObjectUtils.isEmpty(obj);
    }

    /**
     * 对象空判断
     */
    public static boolean isEmpty(Object obj){
        if (obj == null)
            return true;

        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>)obj).isEmpty();
        }
        return false;
    }
}
