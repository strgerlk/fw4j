package com.vbrug.fw4j.common.util;

import com.vbrug.fw4j.common.exception.Fw4jException;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 对象工具类
 * @author vbrug
 * @since 1.0.0
 */
public abstract class ObjectUtils {

    /**
     * 判断对象为空，集合校验size
     */
    public static boolean isNull(Object... objects) {
        if (objects == null) return true;
        for (Object object : objects) {
            if (!ObjectUtils.isEmpty(object))
                return false;
        }
        return true;
    }

    /**
     * 判断对象非空，集合校验size
     */
    public static boolean notNull(Object... objects) {
        if (objects == null) return false;
        for (Object object : objects) {
            if (ObjectUtils.isEmpty(object))
                return false;
        }
        return true;
    }

    /**
     * 对象非空判断
     */
    public static boolean notEmpty(Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }

    /**
     * 对象空判断
     */
    public static boolean isEmpty(Object obj) {
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
            return ((Map<?, ?>) obj).isEmpty();
        }
        return false;
    }

    /**
     * Convert the given array (which may be a primitive array) to an
     * object array (if necessary of primitive wrapper objects).
     * <p>A {@code null} source value will be converted to an
     * empty Object array.
     * @param source the (potentially primitive) array
     * @return the corresponding object array (never {@code null})
     * @throws IllegalArgumentException if the parameter is not an array
     */
    public static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return new Object[0];
        }
        if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }
        int length = Array.getLength(source);
        if (length == 0) {
            return new Object[0];
        }
        Class<?> wrapperType = Array.get(source, 0).getClass();
        Object[] newArray    = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }

    /**
     * Determine if the given objects are equal, returning {@code true} if
     * both are {@code null} or {@code false} if only one is {@code null}.
     * <p>Compares arrays with {@code Arrays.equals}, performing an equality
     * check based on the array elements rather than the array reference.
     * @param o1 first Object to compare
     * @param o2 second Object to compare
     * @return whether the given objects are equal
     * @see Object#equals(Object)
     * @see java.util.Arrays#equals
     */
    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1.getClass().isArray() && o2.getClass().isArray()) {
            return arrayEquals(o1, o2);
        }
        return false;
    }

    /**
     * Compare the given arrays with {@code Arrays.equals}, performing an equality
     * check based on the array elements rather than the array reference.
     * @param o1 first array to compare
     * @param o2 second array to compare
     * @return whether the given objects are equal
     * @see #nullSafeEquals(Object, Object)
     * @see java.util.Arrays#equals
     */
    private static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[]) o1, (Object[]) o2);
        }
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[]) o1, (byte[]) o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        return false;
    }


    public static <T> T castObject(Object object, Class<T> clazz) {
        //noinspection unchecked
        return (T) object;
    }

    public static Integer castInteger(Object object) {
        return castObject(object, Integer.class);
    }

    public static Long castLong(Object object) {
        return castObject(object, Long.class);
    }

    public static String castString(Object object) {
        return String.valueOf(object);
    }

    /**
     * 克隆bean
     * [注] 必须含有无参构造函数
     */
    public static <T> T clone(T obj) throws Exception {
        Assert.notNull(obj, "Obj must not be null");
        T clone = (T) obj.getClass().newInstance();
        PropertyDescriptor[] descriptors = Introspector.getBeanInfo(obj.getClass())
                .getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
            Method readMethod  = descriptor.getReadMethod();
            Method writeMethod = descriptor.getWriteMethod();
            if (Objects.isNull(readMethod) || Objects.isNull(writeMethod))
                continue;
            Object value = readMethod.invoke(obj);
            if (!Objects.isNull(value))
                writeMethod.invoke(clone, value);
        }
        return clone;
    }
}
