package com.vbrug.fw4j.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据集合
 * @author vbrug
 * @since 1.0.0
 */
public class DataMap<K, V> {

    private final Map<K, V> map;

    public DataMap() {
        this(new HashMap<K, V>());
    }

    public DataMap(Map<K, V> map) {
        this.map = map;
    }

    public DataMap<K, V> put(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    public V get(K key) {
        return this.map.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(K key, Class<T> clazz) {
        return (T) this.map.get(key);
    }
}
