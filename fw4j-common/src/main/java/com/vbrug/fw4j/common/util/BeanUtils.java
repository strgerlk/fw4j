package com.vbrug.fw4j.common.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Bean工具
 *
 * @author liukong
 * @since 1.0
 */
public abstract class BeanUtils {

    /**
     * 克隆bean
     *
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T clone(T t) throws Exception {
        T clone = (T) t.getClass().newInstance();
        PropertyDescriptor[] descriptors = Introspector.getBeanInfo(t.getClass())
                .getPropertyDescriptors();
        for (PropertyDescriptor descriptor : descriptors) {
            Method readMethod = descriptor.getReadMethod();
            Method writeMethod = descriptor.getWriteMethod();
            if (Objects.isNull(readMethod) || Objects.isNull(writeMethod))
                continue;
            Object value = readMethod.invoke(t);
            if (!Objects.isNull(value))
                writeMethod.invoke(clone, value);
        }
        return clone;
    }
}