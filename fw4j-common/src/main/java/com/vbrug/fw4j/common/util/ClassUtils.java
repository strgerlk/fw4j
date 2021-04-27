package com.vbrug.fw4j.common.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

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
}
