package com.vbrug.fw4j.common.util;

import com.vbrug.fw4j.common.exception.Fw4jException;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;

/**
 * Class工具类
 * @author vbrug
 * @since 1.0.0
 */
public class ClassUtils {

    /**
     * 获取object的属性描述器
     * @param clazz 目标类
     * @return 属性描述器集合
     */
    public static PropertyDescriptor[] getPropertyDescriptor(Class<?> clazz) {
        try {
            return Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取object的某一属性描述器
     * @param clazz 目标类
     * @param name  属性名称
     * @return 属性描述器
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String name) {
        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (name.equals(pd.getName()))
                    return pd;
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据参数构建对象
     * @param tClass
     * @param args
     * @param <T>
     * @return 结果类
     */
    @SuppressWarnings("rawtypes")
    public static <T> T newInstance(Class<T> tClass, Object... args) throws Exception {
        Constructor[] declaredConstructors = tClass.getDeclaredConstructors();
        Constructor   constructor          = null;
        outLoop:
        for (Constructor loopConstructor : declaredConstructors) {
            Class[] parameterTypes = loopConstructor.getParameterTypes();
            if (parameterTypes.length == args.length) {
                for (int j = 0; j < args.length; j++) {
                    if (!parameterTypes[j].isInstance(args[j])) {
                        continue outLoop;
                    }
                }
                constructor = loopConstructor;
                break outLoop;
            }
        }
        if (ObjectUtils.isNull(constructor))
            throw new Fw4jException("对象 {} 不存在参数为({})的构造函数", tClass, args);
        return (T) constructor.newInstance(args);
    }
}
