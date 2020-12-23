package com.vbrug.fw4j.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class BeanUtils {

    /**
     * 将源对象属性复制到目标对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        PropertyDescriptor[] targetPds = ClassUtils.getPropertyDescriptor(target);

        for (PropertyDescriptor targetPd : targetPds) {
            if ("class".equals(targetPd.getName()))
                continue;
            Method writeMethod = targetPd.getWriteMethod();
            PropertyDescriptor sourcePd = ClassUtils.getPropertyDescriptor(source, targetPd.getName());
            if (sourcePd != null) {
                Method readMethod = sourcePd.getReadMethod();
                if (readMethod != null) {
                    try {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        writeMethod.invoke(target, value);
                    } catch (Throwable ex) {
                        throw new RuntimeException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                    }
                }
            }
        }
    }

}
