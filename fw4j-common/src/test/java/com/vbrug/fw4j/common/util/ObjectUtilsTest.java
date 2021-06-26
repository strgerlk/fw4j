package com.vbrug.fw4j.common.util;

import java.lang.reflect.InvocationTargetException;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ObjectUtilsTest {

    public static void main(String[] args) throws Exception {
        ObjectUtilsBean objectUtilsBean = ClassUtils.newInstance(ObjectUtilsBean.class, 1, 2);
        System.out.println(objectUtilsBean.getEndNumber());
    }
}
