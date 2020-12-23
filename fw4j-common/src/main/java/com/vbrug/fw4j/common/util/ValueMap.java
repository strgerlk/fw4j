package com.vbrug.fw4j.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 值Map
 * @author vbrug
 * @since 1.0.0
 */
public class ValueMap<K, V> {

    private final Map<K, V> map;

    public ValueMap(){
        map = new HashMap<>();
    }

    public ValueMap<K, V> add(K key, V value){
        this.map.put(key, value);
        return this;
    }

    public Map<K, V> build(){
        return this.map;
    }

}
