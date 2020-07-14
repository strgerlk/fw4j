package com.vbrug.fw4j.common.util;

import java.util.HashMap;

/**
 * Map工具类
 * @author vbrug
 */
public abstract class MapUtils {

    /**
     * 简化值Map集合创建、赋值操作
     * @param clazz1 Key 类型
     * @param clazz2 Value 类型
     * @return 值集合对象
     */
    public static <K, V> ValueMap<K, V> createValueMap(Class<K> clazz1, Class<V> clazz2) {
        return new ValueMap<K, V>();
    }


    /**
     * 值集合，简化赋值操作
     */
    static class ValueMap<K, V> extends HashMap<K, V> {
        public ValueMap<K, V> add(K key, V value){
            this.put(key, value);
            return this;
        }
    }

}

