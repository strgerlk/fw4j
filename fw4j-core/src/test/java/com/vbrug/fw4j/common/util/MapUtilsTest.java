package com.vbrug.fw4j.common.util;

import org.junit.jupiter.api.Test;

/**
 * @author liukong
 */
class MapUtilsTest {

    @Test
    void createValueMap() {
        System.out.println(MapUtils.createValueMap(String.class, Object.class).add("1", 3333).add("2", 4444));
    }
}