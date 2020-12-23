package com.vbrug.fw4j.common.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ClassUtils {

    /**
     * 获取object的属性描述器
     *
     * @param object 目标类
     * @return 属性描述器集合
     */
    public static PropertyDescriptor[] getPropertyDescriptor(Object object){
        try {
            return Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取object的某一属性描述器
     *
     * @param object 目标类
     * @return 属性描述器
     */
    public static PropertyDescriptor getPropertyDescriptor(Object object, String name){
        try {
            PropertyDescriptor[] pds = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
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
