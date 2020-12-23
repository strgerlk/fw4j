package com.vbrug.fw4j.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据集合
 *
 * @author vbrug
 * @since 1.0.0
 */
public class DataMap<K, V> extends HashMap<K, V> implements Cloneable, java.io.Serializable {

    public DataMap() {
        super();
    }

    /**
     * 获取集合
     * @param key 键
     * @return List集合
     */
    @SuppressWarnings("unchecked")
    public List<Map<K, V>> getListMap(String key){
        Object obj = get(key);
        return (List<Map<K, V>>) obj;
    }

    /**
     * <p>
     * Retrieve the identified <code>int</code> value from the <code>JobDataMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a String.
     */
    public Integer getIntegerFromString(String key) {
        Object obj = get(key);

        return new Integer((String) obj);
    }

    /**
     * <p>
     * Retrieve the identified <code>Boolean</code> value from the <code>JobDataMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a String.
     */
    public Boolean getBooleanFromString(String key) {
        Object obj = get(key);

        return Boolean.valueOf((String) obj);
    }

    /**
     * <p>
     * Retrieve the identified <code>Character</code> value from the <code>JobDataMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a String.
     */
    public Character getCharacterFromString(String key) {
        Object obj = get(key);

        return ((String) obj).charAt(0);
    }

    /**
     * <p>
     * Retrieve the identified <code>Double</code> value from the <code>JobDataMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a String.
     */
    public Double getDoubleFromString(String key) {
        Object obj = get(key);

        return new Double((String) obj);
    }

    /**
     * <p>
     * Retrieve the identified <code>Float</code> value from the <code>JobDataMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a String.
     */
    public Float getFloatFromString(String key) {
        Object obj = get(key);

        return new Float((String) obj);
    }

    /**
     * <p>
     * Retrieve the identified <code>Long</code> value from the <code>JobDataMap</code>.
     * </p>
     *
     * @throws ClassCastException if the identified object is not a String.
     */
    public Long getLongFromString(String key) {
        Object obj = get(key);

        return new Long((String) obj);
    }
}
