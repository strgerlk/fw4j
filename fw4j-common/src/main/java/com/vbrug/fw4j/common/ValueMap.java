package com.vbrug.fw4j.common;

import java.util.HashMap;

/**
 * 数据集合
 * @author vbrug
 * @since 1.0.0
 */
public class ValueMap<K, V> extends HashMap<K, V> {

    public ValueMap<K, V> add(K key, V value) {
        this.put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(K key, Class<T> clazz) {
        return (T) this.get(key);
    }
}
